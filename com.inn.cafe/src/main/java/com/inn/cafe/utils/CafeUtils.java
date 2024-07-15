package com.inn.cafe.utils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.misc.TestRig;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inn.cafe.serviceImp.UserServiceImp;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import lombok.Data;
import lombok.extern.java.Log;

public class CafeUtils {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);

	
	private CafeUtils() {
		
	}
			
	public static ResponseEntity<String> getResponseEntity(String responMessage,HttpStatus httpStatus)
	{
		return new ResponseEntity<String>("{\"message\":\""+responMessage+"\"}",httpStatus);

	}
	
	public static String getUUID() {
		
		Date date = new Date();
		long time = date.getTime();
		return "Bill-"+time;

	
	}
	public static JSONArray getJsonArrayFromString(String data) throws JSONException{
		JSONArray jsonArray = new JSONArray(data);
		return jsonArray;				
	}
	public static Map<String, Object> getMapFroMapJson(String data){
		if(!Strings.isNullOrEmpty(data))
		{
			return new Gson().fromJson(data,new TypeToken<Map<String,Object>>(){
			}.getType());
		}
		return new HashMap<>();
	}
	public static Boolean isFileExist(String path) {	
		log.info("Inside isFileExist {}",path);		
		try {
			File file = new File(path);
			return (file != null && file.exists()) ? Boolean.TRUE : Boolean.FALSE;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	
	}
	
	
	
	
	
}
