package com.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.BillService;
import com.util.CryptoUtil;
import com.util.Misc;
//import com.sun.org.apache.bcel.internal.generic.NEW;
import com.vo.Bill;
import com.vo.Users;

@SuppressWarnings("serial")
public class BillAction extends ActionSupport {

	private Bill bill = new Bill();
	private BillService billService;
	private List<Bill> billList = new ArrayList<Bill>();
	private int currentPage;
	private int pageSize;
	private int callHour;
	private int callMinute;
	private int callSecond;
	private String startTime;
	private String callNum;

	private static final String REPORTS = "reports";

	// ************************************************************//

	/**
	 * use,business
	 * 
	 * @return
	 */
	public String findUserBill() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try{
			Integer userId = Integer.valueOf(request.getParameter("userId"));
			Users oneUser = billService.findUserByUserId(userId);

			if (pageSize == 0) {
				pageSize = 8;
			}
			if (currentPage == 0) {
				currentPage = 1;
			}
			List bills = billService.findbillByPhonenum(oneUser.getUserPhonenum(),
					currentPage, pageSize);
			billList = bills;

			request.setAttribute("oneUser", oneUser);
			// request.setAttribute("billList", bills);
			return "bills";
		}catch(Exception e){
			request.setAttribute("oneUser", new Users());
			request.setAttribute("billList", new Bill());
			return "bills";
		}
		
	}

	/**
	 * use
	 * 
	 * @return
	 */

	public void findAllUserBill() {

		HttpServletRequest request = ServletActionContext.getRequest();

		Integer userId = (Integer) request.getSession().getAttribute("userId");
		Users oneUser = billService.findUserByUserId(userId);

		List bills = billService.findbillByPhonenum(oneUser.getUserPhonenum());
		int size = bills != null ? bills.size() : 0;
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.setContentType("text/plain");
			PrintWriter pWriter = response.getWriter();
			pWriter.print(size);
			pWriter.flush();
		} catch (IOException e) {
		}

	}

	/**
	 * 
	 * use
	 */
	@SuppressWarnings({ "deprecation" })
	public void genUserBillReport() {
		HttpServletRequest request = ServletActionContext.getRequest();

		// 2. get user's bill
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		Users user = billService.findUserByUserId(userId);
		if (user == null) {
			return;
		}
		List billLists = billService.findbillByPhonenum(user.getUserPhonenum());

		// 3.gen excel
		String secretStr = CryptoUtil.MD5Hash(user.getUserPhonenum());
		String currentTimeMillis = String.valueOf(System.currentTimeMillis());

		String contextRealPath = ServletActionContext.getRequest().getRealPath(
				File.separator);
		//解决weblogic下路径为NULL的bug
		if(contextRealPath == null){
			String absoluteClassPath = this.getClass().getClassLoader().getResource("/").getPath();
			String relativeClassPath = "WEB-INF/classes/";
			contextRealPath = absoluteClassPath.substring(0,absoluteClassPath.length()-relativeClassPath.length())
					+ File.separator + REPORTS + File.separator ;
		} else {
			contextRealPath = contextRealPath + File.separator +REPORTS +File.separator;
		}
		File file = new File(contextRealPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		StringBuilder sbFilename = new StringBuilder();
		sbFilename.append(secretStr).append("-").append(currentTimeMillis)
				.append(".xls");

		StringBuilder sbRealPath = new StringBuilder();
		sbRealPath.append(contextRealPath).append(sbFilename);
		
		genExcelReport(billLists, user, sbRealPath.toString());

		HttpServletResponse response = ServletActionContext.getResponse();

		try {
			response.setContentType("text/plain");
			PrintWriter pWriter = response.getWriter();
			pWriter.print(sbFilename.toString());
			pWriter.flush();
		} catch (IOException e) {

		}
	}

	/**
	 * 
	 * use
	 */
	@SuppressWarnings("deprecation")
	private void genExcelReport(List<Bill> billLists, Users user,
			String reportRelativePath) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("电子账单");
			sheet.createFreezePane(1, 0);
			sheet.setColumnWidth((short) 0, (short) 6500);
			sheet.setColumnWidth((short) 1, (short) 6500);
			sheet.setColumnWidth((short) 2, (short) 6500);
			sheet.setColumnWidth((short) 3, (short) 6500);
			sheet.setColumnWidth((short) 4, (short) 6500);
			sheet.setColumnWidth((short) 5, (short) 6500);
			sheet.setColumnWidth((short) 6, (short) 6500);

			// *********************************//
			HSSFRow row0 = sheet.createRow(0);
			HSSFCell cell0 = row0.createCell((short) 0);
			cell0.setCellValue("主叫号码");
			cell0 = row0.createCell((short) 1);
			cell0.setCellValue("所属套餐");
			cell0 = row0.createCell((short) 2);
			cell0.setCellValue("开始时间");
			cell0 = row0.createCell((short) 3);
			cell0.setCellValue("结束时间");
			cell0 = row0.createCell((short) 4);
			cell0.setCellValue("被叫号码");
			cell0 = row0.createCell((short) 5);
			cell0.setCellValue("被叫类型");
			cell0 = row0.createCell((short) 6);
			cell0.setCellValue("通话计费");
			// *********************************//

			for (int i = 0; i < billLists.size(); i++) {
				Bill bill = billLists.get(i);

				String billPhonenum = bill.getBillPhonenum() == null ? "N/A"
						: bill.getBillPhonenum();
				String billBusiness = bill.getBillBusiness() == null ? "N/A"
						: bill.getBillBusiness();

				Timestamp billStarttime = bill.getBillStarttime();
				String billStarttimeStr = "N/A";
				if (billStarttime != null) {
					billStarttimeStr = billStarttime.toString();
				}

				Timestamp billEndtime = bill.getBillEndtime();
				String billEndtimeStr = "N/A";
				if (billEndtime != null) {
					billEndtimeStr = billEndtime.toString();
				}

				String billCallnum = bill.getBillCallnum() == null ? "N/A"
						: bill.getBillCallnum();

				String billTypeStr = "N/A";
				if (bill.getBillType() != null) {
					if (bill.getBillType() == 0) {
						billTypeStr = "市话";
					} else {
						billTypeStr = "长途";
					}
				}

				String billCostStr = "N/A";
				if (bill.getBillCost() != null) {
					billCostStr = String.valueOf(bill.getBillCost());
				}

				HSSFRow row = sheet.createRow(i + 1);
				HSSFCell cell = row.createCell((short) 0);
				cell.setCellValue(billPhonenum);
				cell = row.createCell((short) 1);
				cell.setCellValue(billBusiness);
				cell = row.createCell((short) 2);
				cell.setCellValue(billStarttimeStr);
				cell = row.createCell((short) 3);
				cell.setCellValue(billEndtimeStr);
				cell = row.createCell((short) 4);
				cell.setCellValue(billCallnum);
				cell = row.createCell((short) 5);
				cell.setCellValue(billTypeStr);
				cell = row.createCell((short) 6);
				cell.setCellValue(billCostStr);

			}

			FileOutputStream output = new FileOutputStream(reportRelativePath);
			wb.write(output);
			output.flush();
			output.close();
		} catch (Exception e) {
		}
	}

	/**
	 * use
	 * 
	 */
	public void downloadUserBillReport() {
		// 2.
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileName = request.getParameter("path");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(ServletActionContext.getServletContext()
				.getMimeType(fileName));
		response.setHeader("Content-Disposition", "attachment;filename="
				+ fileName);
		
		String fullFileName = ServletActionContext.getServletContext()
				.getRealPath(
						File.separator + REPORTS + File.separator + fileName);
		//解决weblogic下路径为NULL的bug
		if(fullFileName == null){
			String absoluteClassPath = this.getClass().getClassLoader().getResource("/").getPath();
			String relativeClassPath = "WEB-INF/classes/";
			fullFileName = absoluteClassPath.substring(0,absoluteClassPath.length()-relativeClassPath.length())
					+ File.separator + REPORTS + File.separator + fileName ;
		}
		try {
			InputStream ins = new FileInputStream(fullFileName);
			OutputStream out = response.getOutputStream();
			int b = -1;
			while ((b = ins.read()) != -1) {
				out.write(b);
			}
			ins.close();
			out.close();
			
		} catch (IOException e) {
		}
	}

	

	// ************************************************************//

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	public int getCallHour() {
		return callHour;
	}

	public void setCallHour(int callHour) {
		this.callHour = callHour;
	}

	public int getCallMinute() {
		return callMinute;
	}

	public void setCallMinute(int callMinute) {
		this.callMinute = callMinute;
	}

	public int getCallSecond() {
		return callSecond;
	}

	public void setCallSecond(int callSecond) {
		this.callSecond = callSecond;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<Bill> getBillList() {
		return billList;
	}

	public void setBillList(List<Bill> billList) {
		this.billList = billList;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	private boolean isAccessible(String filename, String userPhonenum) {

		if (Misc.isEmpty(filename)) {
			return false;
		}

		if (Misc.isEmpty(userPhonenum)) {
			return false;
		}

		String[] temp = filename.split("-");
		if (temp.length < 1) {
			return false;
		}
		String hashFromPath = temp[0];
		if (Misc.isEmpty(hashFromPath)) {
			return false;
		}
		if (CryptoUtil.MD5Hash(userPhonenum).equals(hashFromPath)) {
			return true;
		}
		return false;
	}
}
