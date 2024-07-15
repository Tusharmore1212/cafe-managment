package com.inn.cafe.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.POJO.Product;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.ProductDao;
import com.inn.cafe.service.ProductService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.ProductWrapper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Service
public class ProductServiceImp implements ProductService{

	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	JwtFilter jwtFilter;
	
	
	
	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> reqMap) {
		try {
			if (jwtFilter.isAdmin()) {
                if (validateProductMap(reqMap, false)) {
                	productDao.save(getProductMap(reqMap,false));                
                	return CafeUtils.getResponseEntity("Product Added Successfully.", HttpStatus.OK);             
                }
    			return CafeUtils.getResponseEntity(CafeConstants.Invaid_DATA,HttpStatus.BAD_REQUEST);

            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}



	private Product getProductMap(Map<String, String> reqMap, boolean isAdd) {
		Category category = new Category();
		category.setId(Integer.parseInt(reqMap.get("categoryId")));
		Product product = new Product();
		
		if(isAdd) {
			product.setId(Integer.parseInt(reqMap.get("id")));
		}else {
			product.setStatus("true");
		}
		
		product.setCategory(category);
		product.setName(reqMap.get("name"));
		product.setDescription(reqMap.get("description"));
		product.setPrice(Integer.parseInt(reqMap.get("price")));
		return product;
	}



	private boolean validateProductMap(Map<String, String> reqMap, boolean validateId) {
		if (reqMap.containsKey("name")) {
            if (reqMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
	}



	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProduct() {
		try {
			return new ResponseEntity<>(productDao.getAllProduct(),HttpStatus.OK);	
		}catch (Exception e) {
			
			e.printStackTrace();
		}

		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);		
	
	}



	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> reqMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(validateProductMap(reqMap, true)) {
				java.util.Optional<Product> optional=	productDao.findById(Integer.parseInt(reqMap.get("id")));					
				if(!optional.isEmpty()) {
					Product product = getProductMap(reqMap, true);					
					product.setStatus(optional.get().getStatus());					
					productDao.save(product);
	               	 return CafeUtils.getResponseEntity("Product Updated Successfully", HttpStatus.OK);
				}else {
               	 return CafeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
	
				}
					
				}else {
					return CafeUtils.getResponseEntity(CafeConstants.Invaid_DATA,HttpStatus.BAD_REQUEST);				
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTORIZED_ACCESS,HttpStatus.UNAUTHORIZED);		
			}	
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}



	@Override
	public ResponseEntity<String> deleteProduct(int id) {
		try {
			if(jwtFilter.isAdmin()) {
				java.util.Optional<Product> optional = productDao.findById(id);
				if(!optional.isEmpty()) {
				productDao.deleteById(id);						
				return CafeUtils.getResponseEntity("Product deleted Successfully", HttpStatus.OK);
				}else {
               	 return CafeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
	
				}
		}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTORIZED_ACCESS,HttpStatus.UNAUTHORIZED);		
			}	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}



	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> reqMap) {
		try {
			if(jwtFilter.isAdmin()) {
				java.util.Optional<Product> optional = productDao.findById(Integer.parseInt(reqMap.get("id")));
				if(!optional.isEmpty()) {
					productDao.updateProductStatus(reqMap.get("status"),Integer.parseInt(reqMap.get("id")));
					return CafeUtils.getResponseEntity("Product Status Updated Successfully", HttpStatus.OK);
				}else {
               	 return CafeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
	
				}
		}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTORIZED_ACCESS,HttpStatus.UNAUTHORIZED);		
			}	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);

	}



	@Override
	public ResponseEntity<List<ProductWrapper>> getByCategory(int id) {
		try {
			return new ResponseEntity<>(productDao.getProductByCategory(id),HttpStatus.OK);	
		}catch (Exception e) {
			
			e.printStackTrace();
		}

		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);		
	
	}



	@Override
	public ResponseEntity<ProductWrapper> getProductById(int id) {
		try {
			return new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);	
		}catch (Exception e) {
			
			e.printStackTrace();
		}

		return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);		
	
	}

}
