package com.action;

import com.opensymphony.xwork2.ActionSupport;
import com.service.LockrecordService;
import com.service.UsersService;
import com.util.CryptoUtil;
import com.util.FileProcessUitl;
import com.util.Misc;
import com.vo.Items;
import com.vo.Lockrecord;
import com.vo.Users;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * user action
 *
 * @author Diaz
 *
 */
public class UsersAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private UsersService usersService;
	private LockrecordService lockrecordService;

	private String userName;
	private String userPassword;
	private Users user = new Users();
	private Users oneUser = new Users();
	private int flag;
	private int currentPage;
	// 上传文件集合
	private File[] uploadImage;
	// 上传文件名集合
	private String[] uploadImageFileName;
	// 上传文件内容类型集合
	private String[] uploadImageContentType;
	// 文件上传路径
	private String uploadPath = "/upload";
	private int imageflag;
	private final int MAX_TRY_TIMES = 5;
	private final long LOCKED_TIME = 12 * 60 * 60 * 1000;// 12h

	/**
	 * use
	 *
	 * @return
	 */
	public String findOneUserInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Users oneUser = usersService.findUsersById(new Integer(request.getSession()
				.getAttribute("userId").toString()));
		// 简单脱敏处理
		oneUser.setUserPhonenum(oneUser.getUserPhonenum().substring(0, 3)+"****"+oneUser.getUserPhonenum().substring(7, 11));
		oneUser.setUserRealname(oneUser.getUserRealname().charAt(0)+"****");
		oneUser.setUserEmail(oneUser.getUserEmail().substring(0,1)+"****@"+oneUser.getUserEmail().split("@")[1]);
		if (null == user) {
			return "oneUser";
		}
		oneUser.setUserBalance(((int) (oneUser.getUserBalance() * 100)) / 100.0);
		request.setAttribute("oneUser", oneUser);
		return "oneUser";
	}

	/**
	 * get user info 
	 *
	 * @return
	 */
	public String oneUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Users oneUser = usersService.findById(request.getParameter("userId")
				.toString());
		if (null == oneUser) {
			return "oneUser";
		}
		if (null != oneUser.getUserBalance()) {
			oneUser.setUserBalance(((int) (oneUser.getUserBalance() * 100)) / 100.0);
		}
		// 简单脱敏处理
		oneUser.setUserPhonenum(oneUser.getUserPhonenum().substring(0, 3)+"****"+oneUser.getUserPhonenum().substring(7, 11));
		oneUser.setUserRealname(oneUser.getUserRealname().charAt(0)+"****");
		oneUser.setUserEmail(oneUser.getUserEmail().substring(0,1)+"****@"+oneUser.getUserEmail().split("@")[1]);

		request.setAttribute("oneUser", oneUser);
		return "oneUser";
	}

	// **************************************************************************************//

	/**
	 * find password step1
	 *
	 * @return
	 */
	public String findpwdOne() {

		HttpServletRequest request = ServletActionContext.getRequest();

		if (!Misc.isVCodeValid(request, "vcode")) {
			request.setAttribute("findpwd_error", "验证码错误");
			return "step1";
		}
		Users qeuryUser = usersService.findUserByUserPhonenum(user);
		if (qeuryUser == null) {// user not exits
			request.setAttribute("findpwd_error", "用户不存在");
			return "step1";
		} else {
			if (isCurrentStatusIsLocked(qeuryUser)) {
				request.setAttribute("findpwd_error", "用户已被锁定");
				return "step1";
			} else {
				String question = qeuryUser.getUserQuestion();

				request.getSession().setAttribute("findpwd_user_question",
						question);
				request.getSession().setAttribute("findpwd_user_phone",
						user.getUserPhonenum());

				return "step2";
			}
		}

	}

	/**
	 * find password step2
	 *
	 * @return
	 */
	public String findpwdTwo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		// 1.check whether user in session first
		String findpwdUserPhonenum = (String) request.getSession()
				.getAttribute("findpwd_user_phone");
		if (Misc.isEmpty(findpwdUserPhonenum)) {
			return "step1";
		}

		// 2.
		List<?> userList = usersService
				.findUserByUserPhonenum(findpwdUserPhonenum);
		if (userList.size() == 0) {// user not exits
			request.setAttribute("findpwd_error", "服务器错误");
			return "step1";
		}
		Users queryUser = (Users) userList.get(0);
		String trueAnswer = queryUser.getUserAnswer();
		String userAnswer = user.getUserAnswer();
		// 3.verify user's answer
		if (!trueAnswer.equals(userAnswer)) {

			// 4.add lock record
			addLockrecordByUser(queryUser);
			if (isCurrentStatusIsLocked(queryUser)) {
				request.setAttribute("findpwd_error", "账户已被锁定");
				request.getSession().removeAttribute("findpwd_user_phone");
				request.getSession().removeAttribute("findpwd_user_question");

				return "step1";
			}
			request.setAttribute("findpwd_error", "密保问题回答错误");
			return "step2";
		} else {
			request.getSession().setAttribute("findpwd_isVerified", true);
			return "step3";
		}
	}

	/**
	 * find password step3
	 *
	 * @return
	 */
	public String findpwdThree() {

		HttpServletRequest request = ServletActionContext.getRequest();

		// 1.check whether user in session first
		String findpwdUserPhonenum = (String) request.getSession()
				.getAttribute("findpwd_user_phone");
		if (Misc.isEmpty(findpwdUserPhonenum)) {
			return "step1";
		}

		// 2.check whether is verified
		Object isVerified = request.getSession().getAttribute(
				"findpwd_isVerified");
		if (null == isVerified || false == (boolean)isVerified) {
			request.setAttribute("findpwd_error", "请回答密保问题");
			request.getSession().removeAttribute("findpwd_isVerified");
			return "step2";
		}

		// 3.update user's password and remove lock record
		List<?> userLists = usersService
				.findUserByUserPhonenum(findpwdUserPhonenum);
		if (userLists.size() < 1) {
			return "step1";
		}
		Users queryUser = (Users) userLists.get(0);
		String salt = CryptoUtil.genSaltFromUUID();
		String newPasswordHashStr = CryptoUtil.SHA1HashWithSalt(
				user.getUserPassword(), salt);
		// 3.1 update password
		queryUser.setUserPassword(newPasswordHashStr);
		usersService.modifyUserPassword(queryUser);

		request.getSession().removeAttribute("findpwd_user_phone");
		request.getSession().removeAttribute("findpwd_isVerified");
		request.getSession().removeAttribute("findpwd_user_question");

		// 3.2 remove lock record
		removeLockrecordByUser(queryUser);
		return "step4";

	}

	/**
	 * remove the special user's lock record
	 *
	 * @param user
	 */
	private void removeLockrecordByUser(Users user) {
		if (user == null) {
			throw new NullPointerException("User can't be null");
		}
		Lockrecord lockrecord = lockrecordService.findRecordByUserId(user
				.getUserId());
		if (lockrecord == null) {
			return;
		}
		lockrecordService.removeLockrecord(lockrecord);
	}

	/**
	 * check the special user's status(locked or not)
	 *
	 * @param user
	 * @return true if locked false if not locked
	 */
	private boolean isCurrentStatusIsLocked(Users user) {
		// 1. if the current status is unlocked, return
		if (user.getUserStatus() == 0) {
			return false;
		}
		// 2.if the current status is locked, look furthermore
		Lockrecord lockrecord = lockrecordService.findRecordByUserId(user
				.getUserId());
		if (lockrecord == null) {
			return false;
		}
		// 2.1 remove those lock record which exceed LOCKED_TIME
		lockrecord = removeOutdatedLockrecord(lockrecord);
		// 2.2 the number of record is lower than MAX_TRY_TIMES,
		// update user's status to unlocked
		if (lockrecord.getRecord().split(",").length < MAX_TRY_TIMES) {
			user.setUserStatus(0);
			usersService.modifyUserStatus(user);
			return false;
		}

		return true;
	}

	/**
	 * remove those lock records which are "outdated"
	 *
	 * @param lockrecord
	 * @return
	 */
	private Lockrecord removeOutdatedLockrecord(Lockrecord lockrecord) {

		if (lockrecord == null) {
			throw new NullPointerException(
					"Can't remove record from a null object");
		}
		String record = lockrecord.getRecord();
		String[] timestamps = record.split(",");

		// 1.there is no record, nothing to do
		if (timestamps.length <= 1) {
			return lockrecord;
		}

		long currentTimeMillis = System.currentTimeMillis();
		int size = timestamps.length;
		int index = 0;
		for (; index < size; index++) {
			if (currentTimeMillis - Long.parseLong(timestamps[index]) < LOCKED_TIME) {
				break;
			}
		}
		if (index > 0) {
			StringBuilder sb = new StringBuilder();
			for (; index < size; index++) {
				sb.append(timestamps[index]).append(",");
			}
			String newRecord = sb.toString();
			newRecord = newRecord.substring(0, newRecord.length() - 1);
			lockrecord.setRecord(newRecord);
			lockrecordService.modifyLockrecord(lockrecord);
		}

		return lockrecord;

	}

	private void addLockrecordByUser(Users user) {

		if (user == null) {
			throw new NullPointerException("User can't be null");
		}

		Lockrecord lockrecord = lockrecordService.findRecordByUserId(user
				.getUserId());

		long timestamp = System.currentTimeMillis();

		// 1.there is no record currently, insert a new one
		if (lockrecord == null) {
			Lockrecord newLockrecord = new Lockrecord();
			newLockrecord.setUserId(user.getUserId());
			newLockrecord.setRecord(String.valueOf(timestamp));
			lockrecordService.createLockrecord(newLockrecord);
			return;
		}

		lockrecord = removeOutdatedLockrecord(lockrecord);
		String record = lockrecord.getRecord();

		// 2. is the number of record more than MAX_TRY_TIMES?
		if (record.split(",").length >= MAX_TRY_TIMES) {
			// This should not happen normally, just in case
			if (user.getUserStatus() == 0) {
				user.setUserStatus(1);
				usersService.modifyUserStatus(user);
			}
			return;
		}

		// 3.add a record
		StringBuilder sb = new StringBuilder();
		sb.append(record).append(",").append(String.valueOf(timestamp));
		lockrecord.setRecord(sb.toString());
		lockrecordService.modifyLockrecord(lockrecord);

	}

	// **************************************************************************************//


	/**
	 *
	 *
	 * user login
	 *
	 * @return
	 */
	public String userLogin() {
		//解决登录时数据库connection没事抽风的问题
		int i;
		for(i = 0; i < 5;i++ ){
			if(usersService.isDatabaseAlive()){
				break;
			}
		}
		if(i >= 5){
			return "error";
		}
		//登录时重置sessionid
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().invalidate();
		// 1、从数据库中取出hash密码
		Users userData = usersService.findUserByUserPhonenum(user);
		if (null == userData) {
			flag = 1;
			return "error";
		}
		if (userData.getUserStatus() == 1) {
			flag = 2;
			return "error";
		}
		// 2、将传入的密码加盐hash
		String Salt = CryptoUtil.getSaltFromHashString(userData
				.getUserPassword());
		String inSaltPasswd = CryptoUtil.SHA1HashWithSalt(
				user.getUserPassword(), Salt);
		// 3、对比两者的值是否相同
		if (inSaltPasswd.equals(userData.getUserPassword())) {
			request.getSession().setAttribute("userPhonenum", userData.getUserPhonenum());
			request.getSession().setAttribute("userId", userData.getUserId());
			return findOneUserInfo();
		} else {
			flag = 1;
			return "error";
		}
	}

	/**
	 * user avar upload
	 *
	 * @return
	 * @throws IOException
	 */
	// upload vulnerability
	// author: Dong Bing
	public String userAvarUpload() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null == request.getSession().getAttribute("userId")) {
			return "error";
		}

		if (null != uploadImage && uploadImage.length > 0 && checkAvar(uploadImageFileName, uploadImageContentType)) {
			FileProcessUitl up = new FileProcessUitl();
			String path = up.processFileUpload(uploadPath, uploadImage,
					uploadImageFileName, uploadImageContentType);
			if (null == path || path.length() <= 0) {
				imageflag = 1;
				return findOneUserInfo();
			}
			// 将文件路径存入数据库

			Users u = new Users();
			u = usersService.findUsersById(new Integer(request.getSession()
					.getAttribute("userId").toString()).intValue());
			u.setUserAvarurl(path);
			usersService.modifyUsers(u);
			imageflag = 2;
			return findOneUserInfo();
		} else {
			imageflag = 1;
			return findOneUserInfo();
		}
	}

	// check both the imageFileName and imageContentType
	private boolean checkAvar(String[] uploadImageFileName, String[] uploadImageContentType) {
		boolean checkFilename = false;
		boolean checkContentType = false;
		String[] allowFilenames = {"jpg", "png"};
		String[] allowContentTypes = {"image/jpeg", "image/png"};
		for (String filename: uploadImageFileName) {
			String extName = filename.substring(filename.indexOf(".") + 1);
			if (Arrays.asList(allowFilenames).contains(extName)) {
				checkFilename = true;
			}
		}
		for (String contentType: uploadImageContentType) {
			if (Arrays.asList(allowContentTypes).contains(contentType)) {
				checkContentType = true;
			}
		}
		return checkFilename && checkContentType;
	}

	/**
	 * logout
	 */
	public String logOff() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().invalidate();
		flag=4;
		return "error";
	}


	public String recharge(){
		HttpServletRequest request = ServletActionContext.getRequest();
		List list = (List)usersService.getRechargeItems();
		request.setAttribute("items", list);
		return "recharge";

	}

	/**
	 * 处理充值业务
	 * @return
	 */
	public String processRecharge(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String userid =request.getSession().getAttribute("userId").toString();
		if(null== userid){
			return "rechargeError";
		}
		//	in
		double chargeValue = Double.parseDouble(request.getParameter("cost"));
		String telno = request.getParameter("telno");
		if(chargeValue<=0){
			int flag = 1;
			request.setAttribute("errorMsg","充值金额不正确");
			return "rechargeError";
		}
		List userList = usersService.findUserByUserPhonenum(telno);
		if(userList.size()==0){
			int flag = 1;
			request.setAttribute("errorMsg","找不到要充值的号码");
			return "rechargeError";
		}

		int productId ;
		try{
			productId = Integer.parseInt(request.getParameter("productId"));
		}catch (Exception e){
			request.setAttribute("errorMsg","非法请求");
			int flag = 1;
			return "rechargeError";
		}
		//double dbCost = usersService.getRechargeCostByid(productId);//数据库记录的价格

		Items itemInfo = (Items) usersService.getRechargeItemsByid(productId).get(0);
		String orderId = Misc.genRandomOrderId();
		request.setAttribute("orderId", orderId);
		request.setAttribute("itemInfo", itemInfo);
		request.setAttribute("telno", telno);
		int flag = 0;
		return  "recharge1";


	}

	/**
	 * 处理支付请求
	 * @return
	 */
	// there is a problem for double number compare
	// author: Dong Bing
	public String payOrder(){
		HttpServletRequest request = ServletActionContext.getRequest();;
		if (null == request.getSession().getAttribute("userId")) {
			return "error";
		}
		String orderId = request.getParameter("orderId");
		String cost = request.getParameter("cost");
		String payPwd = request.getParameter("payPwd");
		String telno = request.getParameter("telno");
		int productId =Integer.parseInt(request.getParameter("productId"));
		//验证密码
		String userid = (request.getSession().getAttribute("userId").toString());
		Users users = usersService.findUsersById(Integer.parseInt(userid));
		String dbPwd = users.getUserPhonenum().substring(7,11);//支付密码取手机号后4位
		if(!dbPwd.equals(payPwd)){
			flag =1;
			request.setAttribute("errorMsg","支付密码不正确！");
			return "rechargeError";
		}


		Items itemInfo = (Items) usersService.getRechargeItemsByid(productId).get(0);
		double dbcost = itemInfo.getPcost();
//		if(dbcost!=Double.parseDouble(cost)*100){
		if (new BigDecimal(dbcost).compareTo(new BigDecimal(cost).multiply(new BigDecimal(100))) != 0) {
			flag =1;
			request.setAttribute("errorMsg","非法的订单");
			return "rechargeError";
		}

		Users users2 = (Users) usersService.findUserByUserPhonenum(telno).get(0);
//		users2.setUserBalance(users2.getUserBalance()+Double.parseDouble(cost));
		// logical error
		// author: Dong Bing
		users2.setUserBalance(users2.getUserBalance() + itemInfo.getPdenomination());
		usersService.modifyUserBalance(users2);
		request.setAttribute("orderId", orderId);
		request.setAttribute("itemInfo", itemInfo);
		flag=0;
		return "paySuccess";


	}

	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public int getImageflag() {
		return imageflag;
	}

	public void setImageflag(int imageflag) {
		this.imageflag = imageflag;
	}

	public File[] getUploadImage() {
		return uploadImage;
	}

	public void setUploadImage(File[] uploadImage) {
		this.uploadImage = uploadImage;
	}

	public String[] getUploadImageFileName() {
		return uploadImageFileName;
	}

	public void setUploadImageFileName(String[] uploadImageFileName) {
		this.uploadImageFileName = uploadImageFileName;
	}

	public String[] getUploadImageContentType() {
		return uploadImageContentType;
	}

	public void setUploadImageContentType(String[] uploadImageContentType) {
		this.uploadImageContentType = uploadImageContentType;
	}

	public LockrecordService getLockrecordService() {
		return lockrecordService;
	}

	public void setLockrecordService(LockrecordService lockrecordService) {
		this.lockrecordService = lockrecordService;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public Users getOneUser() {
		return oneUser;
	}

	public void setOneUser(Users oneUser) {
		this.oneUser = oneUser;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
