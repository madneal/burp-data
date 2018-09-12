package com.vo;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
// 2.1 xss
// author: Dong Bing
public class Users implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String userPhonenum;
	private String userPassword;
	private String userRealname;
	private String userAddress;
	private String userEmail;
	private Double userBalance;
	private String userBusiness;
	private String userQuestion;
	private String userAnswer;
	private String userAvarurl;
	private Integer userStatus;	// 1 locked, 0 active

	// Constructors

	/** default constructor */
	public Users() {
	}

	/** full constructor */
	public Users(String userPhonenum, String userPassword, String userRealname,
			String userAddress, String userEmail, Double userBalance,
			String userBusiness,String userAnswer,String userQuestion,Integer userStatus) {
		this.userPhonenum = userPhonenum;
		this.userPassword = userPassword;
		this.userRealname = userRealname;
		this.userAddress = userAddress;
		this.userEmail = userEmail;
		this.userBalance = userBalance;
		this.userBusiness = userBusiness;
		this.userAnswer = userAnswer;
		this.userQuestion = userQuestion;
		this.userStatus = userStatus;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserPhonenum() {
		return StringEscapeUtils.escapeHtml3(this.userPhonenum);
	}

	public void setUserPhonenum(String userPhonenum) {
		this.userPhonenum = userPhonenum;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserRealname() {
		return StringEscapeUtils.escapeHtml3(this.userRealname);
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public String getUserAddress() {
		return StringEscapeUtils.escapeHtml3(this.userAddress);
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserEmail() {
		return StringEscapeUtils.escapeHtml3(this.userEmail);
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Double getUserBalance() {
		return this.userBalance;
	}

	public void setUserBalance(Double userBalance) {
		this.userBalance = userBalance;
	}

	public String getUserBusiness() {
		return StringEscapeUtils.escapeHtml3(this.userBusiness);
	}

	public void setUserBusiness(String userBusiness) {
		this.userBusiness = userBusiness;
	}

	public String getUserQuestion() {
		return userQuestion;
	}

	public void setUserQuestion(String userQuestion) {
		this.userQuestion = userQuestion;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public String getUserAvarurl() {
		return userAvarurl;
	}

	public void setUserAvarurl(String userAvarurl) {
		this.userAvarurl = userAvarurl;
	}
	public Integer getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

}
