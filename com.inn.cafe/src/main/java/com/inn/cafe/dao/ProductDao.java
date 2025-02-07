package com.inn.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.inn.cafe.POJO.Product;
import com.inn.cafe.wrapper.ProductWrapper;

import jakarta.transaction.Transactional;

public interface ProductDao extends JpaRepository<Product,Integer> {

	List<ProductWrapper> getAllProduct();
	@Transactional
	@Modifying
	int updateProductStatus(@Param("status") String status, @Param("id") int id);	
	List<ProductWrapper> getProductByCategory(@Param("id") int id);

	ProductWrapper getProductById(@Param("id")int id);
	
}
