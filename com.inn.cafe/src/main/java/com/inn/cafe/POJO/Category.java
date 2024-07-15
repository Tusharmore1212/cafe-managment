package com.inn.cafe.POJO;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


//@NamedQuery(name="Category.getAllCategory",query="select c from Category c where c.id in (select p.category from Product p where p.status='true')")

@NamedQuery(name="Category.getAllCategory", query="select c from Category c where c.id in (select p.category.id from Product p where p.status='true')")



@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="category")
public class Category implements Serializable {

	private static final long serialVersionUID=1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="name") 
	private String name;
	
	
	
//	public Category() {
//		super();
//		this.id = id;
//		this.name = name;
//	}
	
	public Category() {
		// TODO Auto-generated constructor stub
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

}
