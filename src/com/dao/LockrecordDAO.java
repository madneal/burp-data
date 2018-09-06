package com.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.vo.Lockrecord;
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

public class LockrecordDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(LockrecordDAO.class);

	// property constants
	public static final String USER_ID = "userId";
	public static final String RECORD = "record";
	

	protected void initDao() {
		// do nothing
	}

	public void save(Lockrecord lockrecord) {
		log.debug("saving Lockrecord instance");
		try {
			getHibernateTemplate().save(lockrecord);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	
	public void delete(Lockrecord lockrecord) {
		this.getHibernateTemplate().delete(lockrecord);
	}



	public void update(Lockrecord transientInstance) {
		log.debug("saving Lockrecord instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}

	}




	public Lockrecord findById(java.lang.Integer id) {
		log.debug("getting Users instance with id: " + id);
		try {
			Lockrecord instance = (Lockrecord) getHibernateTemplate().get("com.vo.Lockrecord", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Lockrecord instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Lockrecord as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public Lockrecord findByUserId(Integer userId) {
		String hql = "from Lockrecord  where user_id=:userid";
		Query query = getSession().createQuery(hql);
		 query.setLong("userid", userId);
		// query.setString("pass", a.getUserPassword());
		 Lockrecord lockrecord = (Lockrecord) query.uniqueResult();
		return lockrecord;
	}



	public List findAll() {
		log.debug("finding all Users instances");
		try {
			String queryString = "from Lockrecord";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Lockrecord merge(Users detachedInstance) {
		log.debug("merging Lockrecord instance");
		try {
			Lockrecord result = (Lockrecord) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Lockrecord instance) {
		log.debug("attaching dirty Lockrecord instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Lockrecord instance) {
		log.debug("attaching clean Lockrecord instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static LockrecordDAO getFromApplicationContext(ApplicationContext ctx) {
		return (LockrecordDAO) ctx.getBean("LockrecordDAO");
	}
}