package com.dao;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.util.JRStyledText;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.vo.Users;

/**
 * A data access object (DAO) providing persistence and search support for Users
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.vo.Users
 * @author MyEclipse Persistence Tools
 */

public class UsersDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(UsersDAO.class);
	// property constants
	public static final String USER_PHONENUM = "userPhonenum";
	public static final String USER_PASSWORD = "userPassword";
	public static final String USER_REALNAME = "userRealname";
	public static final String USER_ADDRESS = "userAddress";
	public static final String USER_EMAIL = "userEmail";
	public static final String USER_BALANCE = "userBalance";
	public static final String USER_BUSINESS = "userBusiness";
	public static final String USER_QUESTION = "userQuestion";
	public static final String USER_ANSWER = "userAnswer";

	protected void initDao() {
		// do nothing
	}

	public void save(Users transientInstance) {
		log.debug("saving Users instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	
	//listɾ
	public void delete(List list){
		       for(int i=0;i<list.size();i++){
		    	   Users user = new Users();
		    	   user.setUserId((Integer)list.get(i));
		    	   this.getHibernateTemplate().delete(user);
		       }
	}
	
	/**
	 *
	 *
	 * @param phone
	 * @return
	 */
	public String  UserPhoneTest(String phone){
		String hql="from Users  where userPhonenum=:userPhonenum";
		Query query=getSession().createQuery(hql);
		query.setString("userPhonenum", phone);
		
		List  phoneList = query.list();
		if(phoneList.size()==0){
			return "ok";
		}else
		return "exist";
	}

	public String CheckUserPhone(String phone) {
		String hql = "from Users  where userPhonenum=:userPhonenum";
		Query query = getSession().createQuery(hql);
		query.setString("userPhonenum", phone);

		List phoneList = query.list();
		if (phoneList.size() != 0) {
			return "exist";
		} else
			return "empty";
	}

	/**
	 *
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	 public  List  findAllUsers(int currentPage ,int pageSize){
		 List usersList = new ArrayList();
		 String hql ="from Users ";
		 Query query =getSession().createQuery(hql);
		 query.setFirstResult((currentPage-1)*pageSize);
		 query.setMaxResults(pageSize);
		 usersList =query.list();
		 return usersList;
	 
	 }
	
	public void updateUsersBill(Users transientInstance){
		log.debug("saving Users instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
}
	public void updateUsersBusiness(Users transientInstance){
		log.debug("saving Users instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	
	}

	public void updateUserPassword(Users transientInstance) {
		log.debug("saving Users instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}

	}

	public void updateUserStatus(Users transientInstance) {
		log.debug("saving Users instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}

	}
	public  void updateUserBalance(Users transientInstance){
		try{
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");

		}catch (RuntimeException e){
			throw e;
		}
	}
	
	public void update(Users transientInstance) {
		log.debug("saving Users instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	
	}
	public void delete(Users persistentInstance) {
		log.debug("deleting Users instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	
	public Users findByUserPhonenum(Users a){
		String hql="from Users  where user_phonenum=:userPhonenum";
		Query query=getSession().createQuery(hql);
		query.setString("userPhonenum", a.getUserPhonenum());
		Users user = (Users)query.uniqueResult();	
		return user;
	}
	
	
	
	/**
	 * use
	 * @param id
	 * @return
	 */
	public Users findById(java.lang.Integer id) {
		log.debug("getting Users instance with id: " + id);
		try {
			Users instance = (Users) getHibernateTemplate().get("com.vo.Users",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * 使用，查询用户信息
	 * @param id
	 * @return
	 */
	// 1.1 Sql injection
	// Author: Dong Bing
	public Users findByStringId(String id) {
//		String hql="from Users  where user_id='"+ id+"'";
		String hql = "from Users where user_id=:id";
		Query query=getSession().createQuery(hql);
		query.setString("id", id);
		Users user = (Users)query.uniqueResult();	
		return user;
	}
	
	

	public List findByExample(Users instance) {
		log.debug("finding Users instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Users instance with property");
		try {
			String queryString = "from Users as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserPhonenum(Object userPhonenum) {
		return findByProperty(USER_PHONENUM, userPhonenum);
	}

	public List findByUserPassword(Object userPassword) {
		return findByProperty(USER_PASSWORD, userPassword);
	}

	public List findByUserRealname(Object userRealname) {
		return findByProperty(USER_REALNAME, userRealname);
	}

	public List findByUserAddress(Object userAddress) {
		return findByProperty(USER_ADDRESS, userAddress);
	}

	public List findByUserEmail(Object userEmail) {
		return findByProperty(USER_EMAIL, userEmail);
	}

	public List findByUserBalance(Object userBalance) {
		return findByProperty(USER_BALANCE, userBalance);
	}

	public List findByUserBusiness(Object userBusiness) {
		return findByProperty(USER_BUSINESS, userBusiness);
	}

	public List findAll() {
		log.debug("finding all Users instances");
		try {
			String queryString = "from Users";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Users merge(Users detachedInstance) {
		log.debug("merging Users instance");
		try {
			Users result = (Users) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Users instance) {
		log.debug("attaching dirty Users instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Users instance) {
		log.debug("attaching clean Users instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UsersDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UsersDAO) ctx.getBean("UsersDAO");
	}
	
	public void isDatabaseAlive(){
		String hql="from Users";
		Query query=getSession().createQuery(hql);
		query.list();
	}

	/**
	 * Items
	 * @return
	 */
	public List findRechargeAll(){
		String hql = "from Items";
		try {
			 return getHibernateTemplate().find(hql);
		}catch (RuntimeException e){
			throw e;
		}
	}

	public List findRechargeById(int id){
		String hql = "from Items where id=?";
		try{
			return getSession().createQuery(hql).setParameter(0, id).list();
		} catch (RuntimeException e){
			throw e;
		}
	}

	public double findRechargeCostByid(int id){
		String hql = "select Items.pcost from Items  where id=?";
		try {
			return (double)(getSession().createQuery(hql).setParameter(0,id).uniqueResult());
		} catch (RuntimeException e){
			throw e;
		}
	}
}