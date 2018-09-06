package com.util;



import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionContext;

public class CSRFProtectUtil {

	/**
	 * protect rand str
	 * @return
	 */
	private String getTokenByRand(){
		return CryptoUtil.MD5Hash(CryptoUtil.genSaltFromUUID());
	}
	
	/**
	 * Session deal
	 */
	public static String genToken(HttpServletRequest request){
		CSRFProtectUtil csrfProtectUtil = new CSRFProtectUtil();
		String tokenStr = csrfProtectUtil.getTokenByRand();
		request.getSession().setAttribute("token", tokenStr);
		return tokenStr;
	}
	
	/**
	 * check token
	 */
	public static boolean checkToken(String token,HttpServletRequest request){
		if(null != token && !token.isEmpty() && null != request.getSession().getAttribute("token")){
			if(!token.equals(request.getSession().getAttribute("token"))){
				request.getSession().setAttribute("token", null);
				return false;
			}else{
				request.getSession().setAttribute("token", null);
				return true;
			}
		}
		return false;
	}
}
