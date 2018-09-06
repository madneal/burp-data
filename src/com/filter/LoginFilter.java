package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter(sso)
 */
//@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest httprequest = (HttpServletRequest) request;  
        HttpServletResponse httpresponse = (HttpServletResponse) response;  
          
        HttpSession session = httprequest.getSession();  
        String path = httprequest.getRequestURI();  
        
        if (path.endsWith("userLogin.action")
        		||path.endsWith("/index.jsp")
        		||path.endsWith("/userRegist.jsp")
        		||path.contains("forgetpwdStep")
        		||path.contains("findpwd")) {  
            chain.doFilter(httprequest, httpresponse);  
        } else {
        	boolean isLogin = false;
        	if(null != session){
            	if(null != session.getAttribute("userPhonenum") 
            			&& null != session.getAttribute("userId")){
                	isLogin = true;
                }
            }
        	if (isLogin) {  
                chain.doFilter(httprequest, httpresponse);  
            } else {  
                session.setAttribute("preurl", httprequest.getRequestURI());  
                httpresponse.sendRedirect("index.jsp"); 
            }  
        }
        
        
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
