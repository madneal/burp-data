package com.service;


import com.dao.LockrecordDAO;
import com.vo.Lockrecord;

public class LockrecordService {

	private LockrecordDAO lockrecordDAO;

	public LockrecordDAO getLockrecordDAO() {
		return lockrecordDAO;
	}

	public void setUnumDao(LockrecordDAO lockrecordDAO) {
		this.lockrecordDAO = lockrecordDAO;
	}

	public void createLockrecord(Lockrecord lockrecords) {

		lockrecordDAO.save(lockrecords);

	}

	public void modifyLockrecord(Lockrecord lockrecord) {

		lockrecordDAO.update(lockrecord);

	}
	
	public void removeLockrecord(Lockrecord lockrecord){
		lockrecordDAO.delete(lockrecord);
	}

	public Lockrecord findRecordByUserId(Integer userId) {

		return lockrecordDAO.findByUserId(userId);

	}


	public void setLockrecordDAO(LockrecordDAO lockrecordDAO) {
		this.lockrecordDAO = lockrecordDAO;
	}
	
	

	
}
