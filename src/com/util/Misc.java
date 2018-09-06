package com.util;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Misc {

	
	/**
	 * verify vcode
	 * @param request
	 * @param vCodeName verify code parameter name
	 * @return
	 */
	public static boolean isVCodeValid(HttpServletRequest request, String vCodeName) {
		if(request == null){
			throw new NullPointerException("Can not retrive captcha from a null HttpServletRequest object");
		}
		String vcode = request.getParameter(vCodeName);
		String vcodeInSession = (String) request.getSession().getAttribute("vcode");

		if (!isEmpty(vcodeInSession)) {
			// 1. remove vcode in memory
			request.getSession().removeAttribute("vcode");
			// 2. check vcode
			if (vcodeInSession.equalsIgnoreCase(vcode)) {
				return true;
			}
		}
		return false;
	}
	
	
	public  static boolean isEmpty(String str){
		if(str == null || str.length() <= 0){
			return true;
		}
		return false;
	}

	public static String genRandomOrderId(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		StringBuffer sb =new StringBuffer();
		sb.append(sdf.format(new Date()));
		for(int i=0;i<10;i++) {
			sb.append( (int)(Math.random() * 9));
		}
		return sb.toString();
	}
	

}
