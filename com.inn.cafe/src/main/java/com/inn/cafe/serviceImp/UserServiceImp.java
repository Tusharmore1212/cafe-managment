package com.inn.cafe.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.inn.cafe.JWT.CustomerUsersDetailsService;
import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.JWT.JwtUtil;
import com.inn.cafe.POJO.User;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.UserDao;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.utils.EmailUtils;
import com.inn.cafe.wrapper.UserWrapper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import jakarta.transaction.UserTransaction;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class UserServiceImp implements UserService {
	
    @Autowired
    UserDao userDao;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    AuthenticationManager authenticationManager;    
    
    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;
   
    @Autowired
    JwtFilter jwtFilter;
    
    @Autowired
    JwtUtil jwtUtil;  
    
    
    @Autowired
    EmailUtils emailUtils;    
    
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		log.info("Inside signup{}",requestMap);
		try {
		if(validateSignUpMap(requestMap))
		{
			User user = userDao.findByEmailId(requestMap.get("email"));
			if(Objects.isNull(user))
			{
				userDao.save(getUserFromMap(requestMap));	
				return CafeUtils.getResponseEntity("Succesfully Registered",HttpStatus.OK);	
			}
			else {
				return CafeUtils.getResponseEntity("Email already exists",HttpStatus.BAD_REQUEST);	
				}
		}else 
		{
				return CafeUtils.getResponseEntity(CafeConstants.Invaid_DATA,HttpStatus.BAD_REQUEST);		
		}
		}catch (Exception e) {

			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

	
	private boolean validateSignUpMap(Map<String, String> requestMap) {
		if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password"))
		{ return true;
		}
		return false;
		}
	
	private User getUserFromMap(Map<String, String>reqMap)
	{
		User user = new User();
		user.setName(reqMap.get("name"));	
		user.setContactNumber(reqMap.get("contactNumber"));
		user.setEmail(reqMap.get("email"));
		user.setPassword(reqMap.get("password"));
		user.setStatus("false");
		user.setRole("user");
		return user;
	
	
	
	}



	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
		log.info("Inside login");
		try{
			org.springframework.security.core.Authentication auth= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
			
			if(auth.isAuthenticated()) {
				if(customerUsersDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
					return new ResponseEntity<String>("{\"token\":\""+jwtUtil.generateToken(customerUsersDetailsService.getUserDetails().getEmail(),customerUsersDetailsService.getUserDetails().getRole())+"\"}",HttpStatus.OK);
			}
				else {
					return new ResponseEntity<String>("{\"message\":\""+"wait for admin approval."+"\"}",HttpStatus.BAD_REQUEST);		}
			
			}
		}catch (Exception e) {
			log.error("{}",e);		
		};
		return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}",HttpStatus.BAD_REQUEST);		

	}



	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {
		try {
			if(jwtFilter.isAdmin()) {
				return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);				
			}else {
				return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
						
			}
					
					
		}catch (Exception e) {
			e.printStackTrace();		
			}
		return  new ResponseEntity <>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

	}



	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {
			
			if(jwtFilter.isAdmin()) {
			java.util.Optional<User> optional =	userDao.findById(Integer.parseInt(requestMap.get("id")));
			if(!optional.isEmpty()) {
				userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
				sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(),userDao.getAllAdmin());			
						
					
						return CafeUtils.getResponseEntity("User Status Updated Scuccessfully", HttpStatus.OK);				
			}else {
				CafeUtils.getResponseEntity("User id does not exist", HttpStatus.OK);
				
			}
			}	
			else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
						
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);
}



	private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
		allAdmin.remove(jwtFilter.getCurrentUser());
		if(status!=null && status.equalsIgnoreCase("true")) {
			emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Approved","USER:- "+user+"\n is approved by \nADMIN:-"+ jwtFilter.getCurrentUser(),allAdmin);
			
			
		}else {
			
			emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Disabled","USER:- "+user+"\n is Disabled by \nADMIN:-"+ jwtFilter.getCurrentUser(),allAdmin);

			
		}
				
	
	}



	@Override
	public ResponseEntity<String> checkToken() {
		
		return CafeUtils.getResponseEntity("true", HttpStatus.OK);		
	}



	@Override
	public ResponseEntity<String> changePassword(Map<String, String> reMap) {
		try {
			
			User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
			if(!userObj.equals(null)) {
				if(userObj.getPassword().equals(reMap.get("oldPassword"))) {
					userObj.setPassword(reMap.get("newPassword"));
					userDao.save(userObj);
					return CafeUtils.getResponseEntity("Password Updated Successfully", HttpStatus.OK);	
				}
				return CafeUtils.getResponseEntity("Incorrect old password",HttpStatus.BAD_REQUEST);	

			}
			return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);	
			
		}catch (Exception e) {
			e.printStackTrace();		
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}



	@Override
	public ResponseEntity<String> forgotPassword(Map<String, String> reMap) {
		
		try {
			User user=  userDao.findByEmail(reMap.get("email"));		
			if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
				emailUtils.forgotMail(user.getEmail(), "Credential by Cafe Management System", user.getPassword());				
			return CafeUtils.getResponseEntity("Check your mail for Creentials.", HttpStatus.OK);	
						
			}
		
		}catch (Exception e) {
			e.printStackTrace();		

		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
