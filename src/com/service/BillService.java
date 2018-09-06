package com.service;

import java.util.List;

//import javax.xml.registry.infomodel.User;

import com.dao.BillDAO;
import com.dao.UsersDAO;
import com.vo.Bill;
import com.vo.Users;

public class BillService {

	private BillDAO billDAO;
	private UsersDAO userDAO;

	public UsersDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UsersDAO userDAO) {
		this.userDAO = userDAO;
	}

	public BillDAO getBillDAO() {
		return billDAO;
	}

	public void setBillDAO(BillDAO billDAO) {
		this.billDAO = billDAO;
	}

	public List findbillByPhonenum(String billPhonenum, int currentPage,
			int pageSize) {

		return billDAO.findByBillPhonenum(billPhonenum, currentPage, pageSize);

	}

	public List findbillByPhonenum(String billPhonenum) {

		return billDAO.findByBillPhonenum(billPhonenum);

	}

	public Users findUserByUserId(Integer userId) {

		return userDAO.findById(userId);

	}

	public void createBill(Bill bill) {

		billDAO.save(bill);

	}

	public void modifyUser(Users user) {

		userDAO.update(user);

	}

}
