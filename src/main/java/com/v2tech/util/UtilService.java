package com.v2tech.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.FileUtils;

import com.v2tech.base.V2GenericException;

public class UtilService {

	static private Properties appConfig = new Properties();
	static boolean loaded = false;
	
	static{
		load();
		
	}
	
	static void load(){
		try {
			appConfig.load(new FileInputStream("appConfig.properties"));
			loaded = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public static String getProperty(String key){
			if(!loaded){
				load();
			}
		return (String) appConfig.get(key);
	}
	
	public static String createFileAndReturnUrl(DataHandler dataHandler, String fileName, String tenderId){
		String fileServerFolder = getProperty("fileServerPath");
		File tenderDir = new File(fileServerFolder + tenderId);
		
		
		try {
			FileUtils.forceMkdir(tenderDir);
			String filePath = fileServerFolder + tenderId+File.separator+fileName;
			OutputStream os;
			os = new FileOutputStream(new File(filePath));
			dataHandler.writeTo(os);
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new V2GenericException("Can not create file",e);
		}
		String context = getProperty("fileServerWebContextURL");
		String fileUrl = context + tenderId+"/"+fileName;
		return fileUrl;
	}
	
	/**
	 * The method extracts the file name (sans extension) of the input file passed to this service
	 * @param header
	 * @return
	 */
	 public static String getFileName(MultivaluedMap<String, String> header) {
		 
	        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
	 
	        for (String filename : contentDisposition) {
	            if ((filename.trim().startsWith("filename"))) {
	 
	                String[] name = filename.split("=");
	 
	                String finalFileName = name[1].trim().replaceAll("\"", "");
	                return finalFileName;
	            }
	        }
	        return "unknown";
	    }
}
