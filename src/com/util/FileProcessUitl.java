package com.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

/**
 * file upload util
 * @author nobody
 *
 */
public class FileProcessUitl {
	
	//允许的文件contentType,必须设置，为空则全部拦截
	private static String[] allowContentType = {"image/jpeg"};
	//不必须设置，默认为1024KB
	private static String allowSizeByKB = "1024";
	
	public String processFileUpload( String path , File[] file , String[] fileName,String []fileContentType) {
		if(fileName==null){
			return null;
		}
		try {
			StringBuffer file_location = new StringBuffer();
			
			//获取保存到 tomcat 服务器上的绝对路径
			ServletContext context = ServletActionContext.getServletContext();
			
			for( int i = 0 ; i < file.length ; i++) {
				//文件上传校验
				String targetDirectory =context.getRealPath(path);    //   -->  C://program files /apache Fundation so ...../项目名/文件夹名
				//解决weblogic下路径为NULL的bug
				if(targetDirectory == null){
					String absoluteClassPath = this.getClass().getClassLoader().getResource("/").getPath();
					String relativeClassPath = "WEB-INF/classes/";
					targetDirectory = absoluteClassPath.substring(0,absoluteClassPath.length()-relativeClassPath.length()) 
							+ path;
				}
				String extName =fileName[i].substring(fileName[i].indexOf("."));
				String GenericFileName=System.nanoTime()+extName;
				
				//获取保存到数据库的相对路径字符串 . 类似于 : /项目名/upload/xxx.jpg
				String location = ServletActionContext.getRequest().getContextPath()+path+"/"+GenericFileName;
				//上传文件
				File target = new File( targetDirectory , GenericFileName );
				
				//文件上传校验
				if(FileProcessUitl.CheckFile(target,fileContentType[i]) == false){
					return null;
				}
				FileUtils.copyFile(file[i], target);
				file_location.append(location).append(",");
			}
			
			String locations = file_location.deleteCharAt(file_location.lastIndexOf(",")).toString();
			return locations;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	private static boolean CheckFile(File inputUploadFile,String fileContentType){
		
		//获取文件的大小
        int iFileSize = (int) inputUploadFile.length(); 
        boolean fileFlag = false;
        
        if(fileContentType == null){ 
          	return false; 
        }
        
		//3、检查文件content-type
        List <String>contentType = new ArrayList<String>();
        for(String c:allowContentType){
        	contentType.add(c);
        }
        
        if(contentType == null || contentType.size() == 0){ 
        	return false; //允许contentType为空，返回false
        }
        
        for(int i = 0; i<contentType.size();i++){
			if(fileContentType.equalsIgnoreCase(contentType.get(i))){
				fileFlag = true;
				break;
			}
        }
        if(!fileFlag){
       	 return false;
        }
 		//4、检查上传文件大小是否符合要求
        long allowSizeByByte;
		if("".equals(allowSizeByKB)){
				allowSizeByByte = 1024L;
		}else{
			allowSizeByByte = Integer.parseInt(allowSizeByKB) * 1024;
		}

		if (iFileSize > allowSizeByByte){
	    	return false;
	    }
		 return true;
	}

}
