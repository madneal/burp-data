package com.vo;

import java.sql.Timestamp;

/**
 * Bill entity. @author MyEclipse Persistence Tools
 */

public class Bill implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer billId;
	private String billBusiness;
	private Timestamp billStarttime;
	private Timestamp billEndtime;
	private Integer billSms;
	private Integer billType;
	private Double billCost;
	private String billPhonenum;
    private String billCallnum;
	// Constructors

	/** default constructor */
	public Bill() {
	}

	public String getBillPhonenum() {
		return billPhonenum;
	}

	public void setBillPhonenum(String billPhonenum) {
		this.billPhonenum = billPhonenum;
	}

	public String getBillCallnum() {
		return billCallnum;
	}

	public void setBillCallnum(String billCallnum) {
		this.billCallnum = billCallnum;
	}

	/** full constructor */
	public Bill(String billBusiness, Timestamp billStarttime,
			Timestamp billEndtime, Integer billSms, Integer billType,
			Double billCost) {
		this.billBusiness = billBusiness;
		this.billStarttime = billStarttime;
		this.billEndtime = billEndtime;
		this.billSms = billSms;
		this.billType = billType;
		this.billCost = billCost;
	}

	// Property accessors

	public Integer getBillId() {
		return this.billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public String getBillBusiness() {
		return this.billBusiness;
	}

	public void setBillBusiness(String billBusiness) {
		this.billBusiness = billBusiness;
	}

	public Timestamp getBillStarttime() {
		return this.billStarttime;
	}

	public void setBillStarttime(Timestamp billStarttime) {
		this.billStarttime = billStarttime;
	}

	public Timestamp getBillEndtime() {
		return this.billEndtime;
	}

	public void setBillEndtime(Timestamp billEndtime) {
		this.billEndtime = billEndtime;
	}

	public Integer getBillSms() {
		return this.billSms;
	}

	public void setBillSms(Integer billSms) {
		this.billSms = billSms;
	}

	public Integer getBillType() {
		return this.billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public Double getBillCost() {
		return this.billCost;
	}

	public void setBillCost(Double billCost) {
		this.billCost = billCost;
	}

}