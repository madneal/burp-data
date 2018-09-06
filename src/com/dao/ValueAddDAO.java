package com.dao;


import com.vo.Va;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class ValueAddDAO extends HibernateDaoSupport{

    @Override
    public void initDao(){

    }

    public List findAllVa(){
        try{
            String hql = "from Va";
            return getHibernateTemplate().find(hql);
        }catch (RuntimeException e){
            throw e;
        }
    }

    public Va findVaByid(Integer id){
        try{
            Va va = (Va)getHibernateTemplate().get("com.vo.Va", id);
            return va;
        }catch (RuntimeException e){
            throw e;
        }
    }


}
