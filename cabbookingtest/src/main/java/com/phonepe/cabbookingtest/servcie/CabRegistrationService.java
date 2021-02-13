package com.phonepe.cabbookingtest.servcie;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.phonepe.cabbookingtest.dao.CabDao;
import com.phonepe.cabbookingtest.dao.CabStatusDao;
import com.phonepe.cabbookingtest.exception.CabBookingNotSupportedInCity;
import com.phonepe.cabbookingtest.exception.CabNotFoundException;
import com.phonepe.cabbookingtest.vo.Cab;
import com.phonepe.cabbookingtest.vo.CabStatus;
import com.phonepe.cabbookingtest.vo.City;
import com.phonepe.cabbookingtest.vo.Status;

@Service
public class CabRegistrationService {
	
	private static Logger logger = LoggerFactory.getLogger(CabRegistrationService.class);
	
	private CabDao cabDao;
	private CityOnbaoardingService cityOnbaoardingService;
	private CabStatusDao cabStatusDao;
	
	
	public CabRegistrationService(CabDao cabDao, CityOnbaoardingService cityOnbaoardingService, CabStatusDao cabStatusDao) {
		super();
		this.cabDao = cabDao;
		this.cityOnbaoardingService = cityOnbaoardingService;
		this.cabStatusDao = cabStatusDao;
	}


	public String registerCab(Cab cab, City location) throws CabBookingNotSupportedInCity {
		logger.info("Registration completed for, cab : {}, city: {}", cab, location);
		if(cityOnbaoardingService.isCityOnBorded(location)) {
			cabDao.addNewCab(cab, location);
			cabStatusDao.addCabStatus(new CabStatus(cab, Status.IDEL, location, LocalDateTime.now()));
		}else {
			logger.error("City:{} is not onboarted for cab booking application", location.getName());
			throw new CabBookingNotSupportedInCity("City:"+ location.getName()+" is not onboarted for cab booking application");
		}
		return "Registration Done";
	}
	
	public Cab getCabByRegId(String regId) throws CabNotFoundException {
		return cabDao.getCabByRegId(regId);
	}
}
