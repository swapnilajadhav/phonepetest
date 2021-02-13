package com.phonepe.cabbookingtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.phonepe.cabbookingtest.exception.CabBookingNotSupportedInCity;
import com.phonepe.cabbookingtest.servcie.CityOnbaoardingService;
import com.phonepe.cabbookingtest.vo.City;

@RestController
public class CityOnboardingController {
	
	private CityOnbaoardingService cityOnbaoardingService;
	
	public CityOnboardingController(CityOnbaoardingService cityOnbaoardingService) {
		super();
		this.cityOnbaoardingService = cityOnbaoardingService;
	}

	@GetMapping("/onBoardCity/{location}")
	public String onBoardCity(@PathVariable String location) throws CabBookingNotSupportedInCity{				
		return cityOnbaoardingService.onBoardCity(new City(location));		
	}
}
