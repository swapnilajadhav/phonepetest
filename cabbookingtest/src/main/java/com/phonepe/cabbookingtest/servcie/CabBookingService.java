package com.phonepe.cabbookingtest.servcie;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.phonepe.cabbookingtest.dao.CabStatusDao;
import com.phonepe.cabbookingtest.exception.NoCabAvailbleException;
import com.phonepe.cabbookingtest.vo.Cab;
import com.phonepe.cabbookingtest.vo.CabStatus;
import com.phonepe.cabbookingtest.vo.City;

@Service
public class CabBookingService {
	private static Logger logger = LoggerFactory.getLogger(CabBookingService.class);

	private CabStatusDao cabStatusDao;

	public CabBookingService(CabStatusDao cabStatusDao) {
		super();
		this.cabStatusDao = cabStatusDao;
	}

	public Cab bookCab(City location) throws NoCabAvailbleException {
		return cabStatusDao.bookCab(location);
	}

	public String tripCompleted(Cab cab, City currentLocation) {
		return cabStatusDao.completeTheTrip(cab, currentLocation);
	}
	
	public String updateCabLocation(Cab cab, City currentLocation) {
		return cabStatusDao.updateCabLocation(cab, currentLocation);
	}
	
	public List<CabStatus> getPriorityList() {
		return cabStatusDao.getCabStatusPriorityList();
	}
	public Set<CabStatus> getUnSupportedRegionList() {
		return cabStatusDao.getUnSupportedRegionList();
	}
}
