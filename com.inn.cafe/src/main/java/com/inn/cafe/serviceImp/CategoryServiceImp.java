package com.inn.cafe.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.CategoryDao;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.CafeUtils;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import lombok.extern.java.Log;

@Service
public class CategoryServiceImp implements CategoryService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    CategoryDao categoryDao;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> reqMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(reqMap, false)) {
                    categoryDao.save(getCategoryFromMap(reqMap, false));
                    return CafeUtils.getResponseEntity("Category Added Successfully", HttpStatus.OK);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            // Consider using a logger to log the exception
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> reqMap, boolean validateId) {
        if (reqMap.containsKey("name")) {
            if (reqMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String, String> reqMap, boolean isAdd) {
        Category category = new Category();
        if (isAdd && reqMap.containsKey("id")) {
            category.setId(Integer.parseInt(reqMap.get("id")));
        }
        category.setName(reqMap.get("name"));
        return category;
    }

	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
		try {
			if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
				log.info("inside if");				
				return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);	
			}
			return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
		}catch (Exception e) {	
			e.printStackTrace();
		}
		return new ResponseEntity(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> reqMap) {
		try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(reqMap, true)) {
                 java.util.Optional<Category> optional= categoryDao.findById(Integer.parseInt(reqMap.get("id")));              
                 if(!optional.isEmpty()) {
                	 categoryDao.save(getCategoryFromMap(reqMap, true));                	 
                	 return CafeUtils.getResponseEntity("Category Updated Successfully", HttpStatus.OK);              	 
                 }else {
                	 return CafeUtils.getResponseEntity("Category id does not exist", HttpStatus.OK);
                 }
                		 
               }
           	 return CafeUtils.getResponseEntity(CafeConstants.Invaid_DATA, HttpStatus.BAD_REQUEST);

            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            // Consider using a logger to log the exception
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    
	}
}
