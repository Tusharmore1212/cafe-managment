package com.inn.cafe.restImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.POJO.Category;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.rest.CategoryRest;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.CafeUtils;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@RestController
public class CategoryRestImp implements CategoryRest{

	@Autowired
	CategoryService categoryService;	
	
	
	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		try {
			return categoryService.addNewCategory(requestMap);				
		}catch (Exception e) {	
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
		try {
			return categoryService.getAllCategory(filterValue);			
		}catch (Exception e) {	
			e.printStackTrace();
		}
		return new ResponseEntity(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	
	}


	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> reMap) {
		try {
			return categoryService.updateCategory(reMap);				
		}catch (Exception e) {	
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
