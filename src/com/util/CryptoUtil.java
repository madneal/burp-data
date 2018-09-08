package com.util;

import java.security.MessageDigest;
import java.util.UUID;

public class CryptoUtil {
	
	private static final String DEFAULT_SALT = "1a2b3c4d";
	
	/**
	 * sha1
	 * @param inStr
	 * @return 
	 */
	private static String SHA1Hash(String inStr){
		MessageDigest sha1 = null;  
        try{  
        	sha1 = MessageDigest.getInstance("SHA1");  
        }catch (Exception e){  
        	return null;  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] shaBytes = sha1.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < shaBytes.length; i++){  
            int val = ((int) shaBytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
	}
	
	/**
	 * sha1 add salt
	 * @param inStr
	 * @param salt
	 * @return
	 */
	public static String SHA1HashWithSalt(String plainText,String salt){
		
		String rawSHA1Hash = CryptoUtil.SHA1Hash(plainText + salt);
		char[] hashChar = rawSHA1Hash.toCharArray();
		char[] saltChar = salt.toCharArray();
		
		for(int i=0; i<8; i++){
			
			hashChar[5*i] = saltChar[i];
		}
		
		return new String(hashChar);
	}
	
	public static String getSaltFromHashString(String hashStr){
		char[] hashStrChar = hashStr.toCharArray();
		char[] salt = new char[8];
		for(int i=0;i<8;i++){
			salt[i] = hashStrChar[5*i];
		}
		
		return new String(salt);
	}
	
	/**
	 * pro salt
	 * @return
	 */
	public static String genSaltFromUUID(){
		String random = UUID.randomUUID().toString().split("-")[0];
		return random != null ? random : DEFAULT_SALT;
	}
	
	
	/**
	 * 
	 * @param inStr
	 * @return
	 */
	public static String MD5Hash(String inStr){
		MessageDigest md5 = null;  
        try{  
        	md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            return null;  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] shaBytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < shaBytes.length; i++){  
            int val = ((int) shaBytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
	}
}
