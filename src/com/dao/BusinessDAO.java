package com.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.vo.Business;

/**
 * A data access object (DAO) providing persistence and search support for
 * Business entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.vo.Business
 * @author MyEclipse Persistence Tools
 */

public class BusinessDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(BusinessDAO.class);
	// property constants
	public static final String BUSINESS_NAME = "businessName";
	public static final String BUSINESS_INFO = "businessInfo";

	protected void initDao() {
		// do nothing
	}

	public void save(Business transientInstance) {
		log.debug("saving Business instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void update(Business transientInstance) {
		log.debug("updating Business instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public void delete(Business persistentInstance) {
		log.debug("deleting Business instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Business findById(java.lang.Integer id) {
		log.debug("getting Business instance with id: " + id);
		try {
			Business instance = (Business) getHibernateTemplate().get(
					"com.vo.Business", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Business instance) {
		log.debug("finding Business instance by example");
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
		log.debug("finding Business instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Business as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBusinessName(Object businessName) {
		return findByProperty(BUSINESS_NAME, businessName);
	}

	public List findByBusinessInfo(Object businessInfo) {
		return findByProperty(BUSINESS_INFO, businessInfo);
	}

	/**
	 * use
	 * @return
	 */
	public List findAll() {
		log.debug("finding all Business instances");
		try {
			String queryString = "from Business";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Business merge(Business detachedInstance) {
		log.debug("merging Business instance");
		try {
			Business result = (Business) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Business instance) {
		log.debug("attaching dirty Business instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Business instance) {
		log.debug("attaching clean Business instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BusinessDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BusinessDAO) ctx.getBean("BusinessDAO");
	}
}