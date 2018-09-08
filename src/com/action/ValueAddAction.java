package com.action;

import com.opensymphony.xwork2.ActionSupport;
import com.service.UsersService;
import com.service.ValueAddService;
import com.util.CSRFProtectUtil;
import com.vo.Users;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class ValueAddAction extends ActionSupport {
    private ValueAddService valueAddService;
    private UsersService usersService;
    private int flag;
    private String token;

    public void setValueAddService(ValueAddService valueAddService) {
        this.valueAddService = valueAddService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    public String getVA(){
        HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
        if(null== httpServletRequest.getSession().getAttribute("userId")){
            return "error";
        }
        token = CSRFProtectUtil.genToken(httpServletRequest);
        List vaList = (List) valueAddService.getVaList();
        httpServletRequest.setAttribute("valist", vaList);
            return "initva";
    }

    /**
     * 办理增值业务
     * @return success
     */
    // logical vulnerability
    // should not pass value in the request parameters
    public String obtain(){
        HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
        if(null == httpServletRequest.getSession().getAttribute("userId")){
            return "error";
        }
        if(false==CSRFProtectUtil.checkToken(token, httpServletRequest)){
            System.out.println("token is invalied");
            return getVA();
        }
        String userid = (httpServletRequest.getSession().getAttribute("userId").toString());
        Users users = usersService.findById(userid);
        double userBalance = users.getUserBalance();
//        int cost =Integer.parseInt(httpServletRequest.getParameter("cost"));
        String vaid = httpServletRequest.getParameter("id");
        int cost = valueAddService.findVaByid(vaid).getVacost();
        if(userBalance<cost){
            flag=1;
            return "success";
        }
        double userLastBalance = userBalance - cost;
        users.setUserBalance(userLastBalance);
        usersService.modifyUserBalance(users);
        httpServletRequest.setAttribute("users",users);
        httpServletRequest.setAttribute("va",valueAddService.findVaByid(vaid));
        flag=0;
        return "success";
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
