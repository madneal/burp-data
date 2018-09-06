package com.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.BusinessService;
import com.service.UsersService;
import com.util.CSRFProtectUtil;
import com.vo.Users;

@SuppressWarnings("serial")
public class BusinessAction extends ActionSupport {

	private BusinessService businessService;
	private UsersService usersService;
	private String token;
	private List busList = new ArrayList();

	/**
	 * find all business
	 */
	public String findAllBusiness() {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		token = CSRFProtectUtil.genToken(request);
		
		List businessList = (List) businessService.findAllBusiness();
		request.setAttribute("businessList", businessList);

		Integer userId = Integer.valueOf(request.getSession().getAttribute("userId")
				.toString());
		Users userList = (Users) usersService.findById(userId);
		request.setAttribute("userList", userList);
		return "selectAll";
	}

	/**
	 * use,update business on users.
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	public String updateuserbusiness() throws UnsupportedEncodingException {

		HttpServletRequest request = ServletActionContext.getRequest();
		if(CSRFProtectUtil.checkToken(token, request) == false){
			return findAllBusiness();
		}
		String userBusiness = request.getParameter("userBusiness");
		Integer userId = Integer.valueOf(request.getSession().getAttribute("userId")
				.toString());

		Users userInfo = (Users) usersService.findById(userId);
		// get businessinfo by id
//		userInfo.setUserBusiness(new String(request.getParameter("userBusiness").getBytes("GBK"),"utf-8"));
		userInfo.setUserBusiness(userBusiness);
		usersService.modifyUsersBusiness(userInfo);
		request.setAttribute("userList1", userInfo);
		return "updatebusiness";

	}

	public BusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(BusinessService businessService) {
		this.businessService = businessService;
	}

	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public List getBusList() {
		return busList;
	}

	public void setBusList(List busList) {
		this.busList = busList;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}