package com.inn.cafe.restImp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.rest.DashboardRest;
import com.inn.cafe.service.DashboardService;


@RestController
class DashboardRestImp implements DashboardRest {

	@Autowired
	DashboardService dashboardService;	
	
	
	@Override
	public ResponseEntity<Map<String, Object>> getCount() {
		// TODO Auto-generated method stub

		return dashboardService.getCount();
		
}

}
