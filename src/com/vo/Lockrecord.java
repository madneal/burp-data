package com.vo;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */

public class Lockrecord implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private String record;
	private Integer userId;
	
	
	// Constructors
	/** default constructor */
	public Lockrecord() {
	}

	/** full constructor */
	public Lockrecord(String record, Integer userId) {
		this.record = record;
		this.userId = userId;
	}

	// Property accessors
	public Integer userId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getUserId() {
		return this.userId;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return this.id;
	}
	
	public void setRecord(String record) {
		this.record = record;
	}
	public String getRecord() {
		return this.record;
	}
	



}