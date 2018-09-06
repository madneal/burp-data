package com.service;

import com.dao.UsersDAO;
import com.vo.Users;

import java.util.List;

public class UsersService {

	private UsersDAO usersDAO;

	public UsersDAO getUsersDAO() {
		return usersDAO;
	}

	public void setUsersDAO(UsersDAO UsersDAO) {
		this.usersDAO = UsersDAO;
	}

	public void createUsers(Users Users) {

		usersDAO.save(Users);

	}

	public void modifyUsers(Users Users) {

		usersDAO.update(Users);

	}

	public Users findUsersById(Integer UsersId) {

		return usersDAO.findById(UsersId);

	}

	public Users findUserByUserPhonenum(Users user) {

		return usersDAO.findByUserPhonenum(user);
	}

	public List findUserByUserPhonenum(String phnum) {

		return usersDAO.findByUserPhonenum(phnum);
	}


	  public void modifyUserPassword(Users user){
		  usersDAO.updateUserPassword(user);
		  
	  }
	  
	  public void modifyUserStatus(Users user){
		  usersDAO.updateUserStatus(user);
		  
	  }
	  public void modifyUserBalance(Users user){
	  	usersDAO.updateUserBalance(user);
	  }
	/**
	 * ͨuse,nop
	 * 
	 * @param userId
	 * @return
	 */
	public Users findById(Integer userId) {
		Users user = usersDAO.findById(userId);
		return user;

	}
	
	/**
	 * find user info by id
	 * @param userId
	 * @return
	 */
	public Users findById(String userId) {
		Users user = usersDAO.findByStringId(userId);
		return user;

	}

	/**
	 * use
	 */

	public void modifyUsersBusiness(Users user) {
		usersDAO.updateUsersBusiness(user);

	}

	public boolean isDatabaseAlive(){
		try{
			usersDAO.isDatabaseAlive();
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public List getRechargeItems(){
		try{
			return usersDAO.findRechargeAll();
		}catch (Exception e){
			throw e;

		}
	}

	public List getRechargeItemsByid(int id){
		try
		{
			return usersDAO.findRechargeById(id);
		}catch (Exception e){
			throw e;
		}
	}

	public double getRechargeCostByid(int id){
		return usersDAO.findRechargeCostByid(id);
	}
}
