package com.phonepe.cabbookingtest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.phonepe.cabbookingtest.exception.CabBookingNotSupportedInCity;
import com.phonepe.cabbookingtest.servcie.CabRegistrationService;
import com.phonepe.cabbookingtest.vo.Cab;
import com.phonepe.cabbookingtest.vo.City;

@RestController
public class CabRegistrationController {
	private static Logger logger = LoggerFactory.getLogger(CabRegistrationController.class);
	private CabRegistrationService cabRegistrationService;
	
	
	public CabRegistrationController(CabRegistrationService cabRegistrationService) {
		super();
		this.cabRegistrationService = cabRegistrationService;		
	}

	@GetMapping("/registerCab/{regId}/{owner}/{location}")
	public String registerCab(@PathVariable String regId, @PathVariable String owner, @PathVariable String location) throws CabBookingNotSupportedInCity{		
		Cab cab = new Cab(regId, owner);
		return cabRegistrationService.registerCab(cab, new City(location));		
	}
}
