package com.phonepe.cabbookingtest.servcie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.phonepe.cabbookingtest.dao.CabStatusDao;
import com.phonepe.cabbookingtest.dao.CityDao;
import com.phonepe.cabbookingtest.vo.City;

@Service
public class CityOnbaoardingService {
	
	private static Logger logger = LoggerFactory.getLogger(CityOnbaoardingService.class);
	private CityDao cityDao;
	private CabStatusDao cabStatusDao;
		
	public CityOnbaoardingService(CityDao cityDao,CabStatusDao cabStatusDao) {
		super();
		this.cityDao = cityDao;
		this.cabStatusDao =cabStatusDao;
	}

	public String onBoardCity(City city) {
		logger.info("Onboarding city: {}", city);
		boolean res = cityDao.addCity(city);
		if(!res) {			
			logger.warn("City was already onboarded: {}", city);
		}else {
			logger.info("handling cabstaus for new city");
			cabStatusDao.handleCityOnboarding(city);
		}
		return "Onboarding completed";
	}

	public boolean isCityOnBorded(City location) {
		return cityDao.isCityOnboarded(location);
	}
}
