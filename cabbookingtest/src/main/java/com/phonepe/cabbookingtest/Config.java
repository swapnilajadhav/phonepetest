package com.phonepe.cabbookingtest;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.phonepe.cabbookingtest.dao.CabStatusDao;
import com.phonepe.cabbookingtest.exception.CabBookingNotSupportedInCity;
import com.phonepe.cabbookingtest.servcie.CabRegistrationService;
import com.phonepe.cabbookingtest.servcie.CityOnbaoardingService;
import com.phonepe.cabbookingtest.vo.Cab;
import com.phonepe.cabbookingtest.vo.City;

@Configuration
@PropertySource(value = { "classpath:cabmetadata/cab.metadata" })
public class Config {
	
	private Environment env;
	private CityOnbaoardingService cityOnBoardingService;
	private CabRegistrationService cabRegistrationService;	
	
	public Config(Environment env, CityOnbaoardingService cityOnBoardingService,
			CabRegistrationService cabRegistrationService) {
		super();
		this.env = env;
		this.cityOnBoardingService = cityOnBoardingService;
		this.cabRegistrationService = cabRegistrationService;
		
	}

	private Logger logger = LoggerFactory.getLogger(Config.class);
	
	/**
	 * load initial data for cab and city from metadata file
	 */
	@PostConstruct
	public void loadCabData() {
		logger.info("env: {}", env.getProperty("cabs") );
		Arrays.asList(env.getProperty("cities").split(",")).forEach((r) ->{
			City city = new City(r);
			cityOnBoardingService.onBoardCity(city);
			logger.info("Onboarding city: {}", city);						
		});
		Arrays.asList(env.getProperty("cabs").split(";")).forEach((r) ->{
			String[] cabInfo = r.split(",");			
			Cab cab = new Cab(cabInfo[0],cabInfo[1]);
			try {
				cabRegistrationService.registerCab(cab, new City(cabInfo[2]));				
			} catch (CabBookingNotSupportedInCity e) {
				logger.error("Exception thrown during metadata loading, {}", e.getMessage());
			}
			logger.info("Onboarding cab: {}", cab);
		});
	}

}
