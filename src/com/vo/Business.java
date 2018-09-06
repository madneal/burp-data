package com.vo;

/**
 * Business entity. @author MyEclipse Persistence Tools
 */

public class Business implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer businessId;
	private String businessName;
	private String businessInfo;

	// Constructors

	/** default constructor */
	public Business() {
	}

	/** full constructor */
	public Business(String businessName, String businessInfo) {
		this.businessName = businessName;
		this.businessInfo = businessInfo;
	}

	// Property accessors

	public Integer getBusinessId() {
		return this.businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessInfo() {
		return this.businessInfo;
	}

	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}

}