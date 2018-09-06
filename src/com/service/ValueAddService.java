package com.service;


import com.dao.ValueAddDAO;
import com.vo.Va;

import java.util.List;

public class ValueAddService {
    private ValueAddDAO valueAddDAO;

    public ValueAddDAO getValueAddDAO() {
        return valueAddDAO;
    }

    public void setValueAddDAO(ValueAddDAO valueAddDAO) {
        this.valueAddDAO = valueAddDAO;
    }

    /**
     * 查询所有的增值业务
     * @return value-add list
     */
    public List getVaList(){
        return valueAddDAO.findAllVa();
    }

    public Va findVaByid(String id){
        Integer vaid = Integer.parseInt(id);
        return valueAddDAO.findVaByid(vaid);
    }
}
