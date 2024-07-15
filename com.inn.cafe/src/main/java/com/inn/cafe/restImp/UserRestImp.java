package com.inn.cafe.restImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.rest.UserRest;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.UserWrapper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
@RestController
public class UserRestImp implements UserRest{

	@Autowired
	UserService userService;
	
//	@Override
//	public ResponseEntity<String> singUp(Map<String, String> requMap) {
//		try {
//			return  userService.signUp(requMap);
//					
//		}catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);
//		}

	@Override
	public ResponseEntity<String> login(Map<String, String> reqMap) {
		try {
			return userService.login(reqMap);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requMap) {
		try {
			return  userService.signUp(requMap);
					
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {
		try {
			return userService.getAllUser();
			} catch (Exception e) {
			e.printStackTrace();		
		}
		return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	@Override
	public ResponseEntity<String> update(Map<String, String> reqMap) {
		try {
			return userService.update(reqMap);		
		}
			catch (Exception e) {
				e.printStackTrace();
			}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);				
	}

	@Override
	public ResponseEntity<String> checkToken() {
	
		try {
			
			return userService.checkToken();	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);				

	}

	@Override
	public ResponseEntity<String> changepassword(Map<String, String> reMap) {
		
		try {
			
			return userService.changePassword(reMap);	
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);				
	
	}

	@Override
	public ResponseEntity<String> forgotPassword(Map<String, String> reMap) {
		try {
			return userService.forgotPassword(reMap);
			
		}catch (Exception e) {

			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);		
		
		
	}
	

}

	

