package com.inn.cafe.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.catalina.mapper.Mapper;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Data
@NoArgsConstructor
public class UserWrapper {

//	UserWrapper user = new UserWrapper(1, "abc", "abc@gmail.com" , "5646", "false");
	

	// Configure ObjectMapper, possibly in a configuration class
	@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }
	
	private int id;
	private String name;
	private String email;
	private String contactNumber;
	private String status;
	
	
	
	public UserWrapper(int id, String name, String email, String contactNumber, String status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.contactNumber = contactNumber;
		this.status = status;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getContactNumber() {
		return contactNumber;
	}



	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}
	
	
	



}
