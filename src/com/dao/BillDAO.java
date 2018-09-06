package com.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.vo.Bill;

/**
 * A data access object (DAO) providing persistence and search support for Bill
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.Bill
 * @author MyEclipse Persistence Tools
 */

public class BillDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(BillDAO.class);
	// property constants
	public static final String BILL_BUSINESS = "billBusiness";
	public static final String BILL_SMS = "billSms";
	public static final String BILL_TYPE = "billType";
	public static final String BILL_COST = "billCost";
	public static final String BILL_PHONENUM = "billPhonenum";
	public static final String BILL_CALLNUM = "billCallnum";

	public void save(Bill transientInstance) {
		log.debug("saving Bill instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Bill persistentInstance) {
		log.debug("deleting Bill instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Bill findById(java.lang.Integer id) {
		log.debug("getting Bill instance with id: " + id);
		try {
			Bill instance = (Bill) getSession().get("com.Bill", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Bill instance) {
		log.debug("finding Bill instance by example");
		try {
			List results = getSession().createCriteria("com.Bill")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Bill instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Bill as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBillBusiness(Object billBusiness) {
		return findByProperty(BILL_BUSINESS, billBusiness);
	}

	public List findByBillSms(Object billSms) {
		return findByProperty(BILL_SMS, billSms);
	}

	public List findByBillType(Object billType) {
		return findByProperty(BILL_TYPE, billType);
	}

	public List findByBillCost(Object billCost) {
		return findByProperty(BILL_COST, billCost);
	}

	/**
	 * 
	 * use
	 * 
	 * @param billPhonenum
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List findByBillPhonenum(Object billPhonenum, int currentPage,
			int pageSize) {
		String queryString = "from Bill where bill_phonenum= ?";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, billPhonenum);
		queryObject.setFirstResult((currentPage - 1) * pageSize);
		queryObject.setMaxResults(pageSize);
		return queryObject.list();
	}

	public List findByBillPhonenum(Object billPhonenum) {
		String queryString = "from Bill where bill_phonenum= ?";
		Query queryObject = getSession().createQuery(queryString);
		queryObject.setParameter(0, billPhonenum);
		return queryObject.list();
	}

	public List findByBillCallnum(Object billCallnum) {
		return findByProperty(BILL_CALLNUM, billCallnum);
	}

	public List findAll() {
		log.debug("finding all Bill instances");
		try {
			String queryString = "from Bill";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * 
	 * 
	 * ��ҳ��ѯ�����˵���Ϣ
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List findAll(int currentPage, int pageSize) {
		log.debug("finding all Bill instances");
		try {
			String queryString = "from Bill";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setFirstResult((currentPage - 1) * pageSize);
			queryObject.setMaxResults(pageSize);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Bill merge(Bill detachedInstance) {
		log.debug("merging Bill instance");
		try {
			Bill result = (Bill) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Bill instance) {
		log.debug("attaching dirty Bill instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Bill instance) {
		log.debug("attaching clean Bill instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}
