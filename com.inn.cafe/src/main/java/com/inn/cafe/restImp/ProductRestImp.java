package com.inn.cafe.restImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.rest.ProductRest;
import com.inn.cafe.service.ProductService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.ProductWrapper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;


@RestController
public class ProductRestImp implements ProductRest{

	@Autowired
	ProductService productService;
	
	
	
	
	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> reqMap) {
		try {
			return productService.addNewProduct(reqMap);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}




	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProduct() {
		try {
			return productService.getAllProduct();
	
		}catch (Exception e) {
			
			e.printStackTrace();
		}

		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);		
	}




	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> reqMap) {
		try {

			return productService.updateProduct(reqMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}




	@Override
	public ResponseEntity<String> deleteProduct(int id) {
		try {

			return productService.deleteProduct(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}




	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> reqMap) {
		try {

			return productService.updateStatus(reqMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}




	@Override
	public ResponseEntity<List<ProductWrapper>> getByCategory(int id) {
		try {

			return productService.getByCategory(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);		

	}




	@Override
	public ResponseEntity<ProductWrapper> getById(int id) {
		try {

			return productService.getProductById(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);		

	}

}
