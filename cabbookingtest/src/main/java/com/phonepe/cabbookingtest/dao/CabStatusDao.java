package com.phonepe.cabbookingtest.dao;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.phonepe.cabbookingtest.exception.IllegalCabStatusException;
import com.phonepe.cabbookingtest.exception.NoCabAvailbleException;
import com.phonepe.cabbookingtest.servcie.CityOnbaoardingService;
import com.phonepe.cabbookingtest.vo.Cab;
import com.phonepe.cabbookingtest.vo.CabStatus;
import com.phonepe.cabbookingtest.vo.City;
import com.phonepe.cabbookingtest.vo.Status;

@Service
public class CabStatusDao {

	private static Logger logger = LoggerFactory.getLogger(CabStatusDao.class);
	private static Set<CabStatus> cabStatusData = new HashSet<>();
	private static Set<CabStatus> cabsInNotSupportedLocation = new HashSet<>();
	private static PriorityQueue<CabStatus> cabStatusPQ = new PriorityQueue<>(
			(o1, o2) -> o1.getStatusTime().compareTo(o2.getStatusTime()));

	private CityOnbaoardingService cityOnBoardingService;
		
	public CabStatusDao(CityOnbaoardingService cityOnBoardingService) {
		super();
		this.cityOnBoardingService = cityOnBoardingService;
	}

	public void addCabStatus(CabStatus cabStatus) {
		cabStatusData.add(cabStatus);
		cabStatusPQ.add(cabStatus);
	}
	
	public List<CabStatus> getCabStatusPriorityList() {
		return cabStatusPQ.stream().collect(Collectors.toList());
	}
	
	public Set<CabStatus> getUnSupportedRegionList(){
		return cabsInNotSupportedLocation;
	}
	
	public Optional<CabStatus> getCabStatus(Cab cab) {		
		return cabStatusData.stream().filter(p -> p.getCab().equals(cab)).findFirst();
	}

	public Cab getCabForBooking() throws NoCabAvailbleException {
		if (cabStatusPQ.isEmpty()) {
			throw new NoCabAvailbleException("No cabs availble for booking");
		}
		return cabStatusPQ.peek().getCab();
	}

	public Cab bookCab(City location) throws NoCabAvailbleException {		
				
		synchronized (cabStatusPQ) {
			if (cabStatusPQ.isEmpty()) {
				logger.error("No cab availble for booking");
				throw new NoCabAvailbleException("No cab availble for booking");
			}
			
			CabStatus cabStatus = null;
			if(cabStatusPQ.peek().getLocation().equals(location)) {
				cabStatus = cabStatusPQ.poll();				
			}else {
				Optional<CabStatus> cabStatusOpt = cabStatusPQ.stream().filter(p -> p.getLocation().equals(location)).findFirst();
				if(!cabStatusOpt.isPresent()) {
					throw new NoCabAvailbleException("No cab availble for booking at given location");
				}
				cabStatus = cabStatusOpt.get();
				cabStatusPQ.remove(cabStatus);
			}
						
			cabStatus.setStatus(Status.ON_TRIP);
			cabStatus.setStatusTime(LocalDateTime.now());
			return cabStatus.getCab();
		}
	}
	
	public String updateCabLocation(Cab cab, City currentLocation) {
		StringBuffer  message = new StringBuffer();;
		cabStatusData.stream().filter(cabStatus -> cabStatus.getCab().equals(cab)).forEach(cabStatus -> {
			
			Optional<CabStatus> cs = getCabStatus(cab);
			
			message.append(cab);
			cabStatus.setLocation(currentLocation);
			if (cs.isPresent()) {
				
				if (cityOnBoardingService.isCityOnBorded(currentLocation)) {
					if (cs.get().getStatus().equals(Status.IDEL) && cabsInNotSupportedLocation.contains(cabStatus)) {
						cabsInNotSupportedLocation.remove(cabStatus);
						cabStatus.setLocation(currentLocation);
						cabStatus.setStatusTime(LocalDateTime.now());
						cabStatusPQ.add(cabStatus);
						message.append(" moved to supported location. Added to waiting list");
					}else if(cabStatusPQ.contains(cabStatus) && cs.get().getStatus().equals(Status.IDEL)){
						cabStatusPQ.remove(cabStatus);
						cabStatus.setLocation(currentLocation);
						cabStatus.setStatusTime(LocalDateTime.now());
						cabStatusPQ.add(cabStatus);
					}else {
						cabStatus.setLocation(currentLocation);
						cabStatus.setStatusTime(LocalDateTime.now());
						message.append(" cab location updated.");
					}
				}else {
					if (cs.get().getStatus().equals(Status.IDEL) && cabStatusPQ.contains(cabStatus)) {
						cabStatusPQ.remove(cabStatus);
						cabStatus.setLocation(currentLocation);
						cabStatus.setStatusTime(LocalDateTime.now());
						cabsInNotSupportedLocation.add(cabStatus);
						message.append(" moved to unsupported location. removing from waiting");
					}else {
						cabStatus.setLocation(currentLocation);
						cabStatus.setStatusTime(LocalDateTime.now());
						message.append(" cab location updated.");
					}
					
				}
				
			}
		});
		return message.toString();
	}
	
	public String completeTheTrip(Cab cab, City currentLocation) throws IllegalCabStatusException{
		
		StringBuffer  message = new StringBuffer();;
		cabStatusData.stream().filter(cabStatus -> cabStatus.getCab().equals(cab)).forEach(cabStatus -> {
						
			Optional<CabStatus> cs = getCabStatus(cab);
			
			message.append(cab);
			cabStatus.setLocation(currentLocation);
			if (cs.isPresent() && cs.get().getStatus().equals(Status.ON_TRIP)) {				
				cabStatus.setStatus(Status.IDEL);
				cabStatus.setStatusTime(LocalDateTime.now());
				if (!cityOnBoardingService.isCityOnBorded(currentLocation)) {
					
					message.append("has completed its trip in unsupported region. To appear in waiting list cab needs to update its location once in supported region.");
					logger.info(message.toString());
					cabsInNotSupportedLocation.add(cabStatus);
				} else {
					message.append("has completed its trip and added to waiting queue for next booking from :").append(currentLocation);
					logger.info(message.toString());
					cabStatusPQ.add(cabStatus);
				}
			} else {
				message.append("Is not on trip and status cannot be updated. status:").append(cs.get().toString());
				logger.error(message.toString() + cs.toString());
				throw new IllegalCabStatusException(message.toString());				
			}
		});
		return message.toString();
	}
}
