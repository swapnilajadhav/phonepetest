package com.phonepe.cabbookingtest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.phonepe.cabbookingtest.exception.CabNotFoundException;
import com.phonepe.cabbookingtest.exception.NoCabAvailbleException;
import com.phonepe.cabbookingtest.servcie.CabBookingService;
import com.phonepe.cabbookingtest.servcie.CabRegistrationService;
import com.phonepe.cabbookingtest.vo.Cab;
import com.phonepe.cabbookingtest.vo.CabStatus;
import com.phonepe.cabbookingtest.vo.City;

@RestController
public class CabBookingController {
	private CabBookingService cabBookingService;
	private CabRegistrationService cabRegistrationService;
	
	public CabBookingController(CabBookingService cabBookingService,
			CabRegistrationService cabRegistrationService) {
		super();
		this.cabBookingService = cabBookingService;
		this.cabRegistrationService = cabRegistrationService;
	}

	@GetMapping("/bookCab/{location}")
	public Cab bookCab(@PathVariable String location) throws NoCabAvailbleException {		
		return cabBookingService.bookCab(new City(location));
	}
	
	@GetMapping("/tripCompleted/{cabRegId}/{location}")
	public String bookCab(@PathVariable String cabRegId,@PathVariable String location) throws NoCabAvailbleException, CabNotFoundException {
		Cab cab = cabRegistrationService.getCabByRegId(cabRegId);
		return cabBookingService.tripCompleted(cab,new City(location));
	}
	
	@GetMapping("/updateCabLocation/{cabRegId}/{location}")
	public String updateCabLocation(@PathVariable String cabRegId,@PathVariable String location) throws NoCabAvailbleException, CabNotFoundException {
		Cab cab = cabRegistrationService.getCabByRegId(cabRegId);
		return cabBookingService.updateCabLocation(cab,new City(location));
	}
	
	@GetMapping(path = "/priorityList", produces="text/html")
	public String getPriorityList(){
		return cabBookingService.getPriorityList().toString() ;
	}
	
	@GetMapping(path = "/unsupportedList", produces="text/html")
	public String getCabsInUnsupportedRegioin(){
		return cabBookingService.getUnSupportedRegionList().toString() ;
	}
}
