package com.inn.cafe.wrapper;

import lombok.Data;

@Data
public class ProductWrapper {

	int id;
	
	String name;
	
	String description;
	
	int price;
	
	String status;
	
	int categoryId;
	
	String categoryName;

	public ProductWrapper()
	{
		
	}
	public ProductWrapper(int id,String name)	{
		this.id = id;
		this.name = name;
	}
	public ProductWrapper(int id,String name,String description, int price)	{
		this.id = id;
		this.name = name;
		this.description=description;
		this.price = price;
	}
	public ProductWrapper(int id,String name,String description,int price,String status,int categoryId,String categoryName)		
	{
		this.id = id;
		this.name = name;
		this.description=description;
		this.price = price;
		this.status=status;
		this.categoryId=categoryId;
		this.categoryName=categoryName;		
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	

}
