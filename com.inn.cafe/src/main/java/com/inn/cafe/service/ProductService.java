package com.inn.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafe.wrapper.ProductWrapper;

public interface ProductService {

	ResponseEntity<String> addNewProduct(Map<String, String>reqMap);
	
	ResponseEntity<List<ProductWrapper>> getAllProduct();
	
	ResponseEntity<String> updateProduct(Map<String, String>reqMap);

	ResponseEntity<String> deleteProduct(int id);

	ResponseEntity<String> updateStatus(Map<String, String>reqMap);

	ResponseEntity<List<ProductWrapper>> getByCategory(int id);

	ResponseEntity<ProductWrapper> getProductById(int id);

}
