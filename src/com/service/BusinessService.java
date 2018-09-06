package com.service;

import com.dao.BusinessDAO;
import com.dao.UsersDAO;
import com.vo.Business;

import java.util.List;



public class BusinessService {
	
	private BusinessDAO businessDAO;
	private UsersDAO usersDAO;
	

	public UsersDAO getUsersDAO() {
		return usersDAO;
	}

	public void setUsersDAO(UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}

	public BusinessDAO getBusinessDAO() {
		return businessDAO;
	}

	public void setBusinessDAO(BusinessDAO businessDAO) {
		this.businessDAO = businessDAO;
	}
   
  /**
   * 
   * use ,find all business
   *
   */
	public List findAllBusiness(){
		return businessDAO.findAll();
		
	}
	
	public void remove(Integer businessId){
		
		businessDAO.delete(businessDAO.findById(businessId));
	}
	
	/**
	 * use,nop
	 * @param id
	 * @return
	 */
	public Business findbyid(Integer id){
		
	return	businessDAO.findById(id);
		
	}
	
	
	public void modify(Business business){
		
		businessDAO.update(business);
		
	}
	
	public void create(Business business){
		
		businessDAO.save(business);
		
	}

}