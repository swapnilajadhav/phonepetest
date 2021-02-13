package com.phonepe.cabbookingtest.dao;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.phonepe.cabbookingtest.exception.CabNotFoundException;
import com.phonepe.cabbookingtest.vo.Cab;
import com.phonepe.cabbookingtest.vo.City;

@Component
public class CabDao {
	private Set<Cab> cabs = new HashSet<>();
		
	public boolean addNewCab(Cab cab, City location) {
		
		return cabs.add(cab);
	}
	public boolean removeCab(Cab cab) {
		return cabs.remove(cab);
	}
	public Stream<Cab> getCabsStream(){
		return cabs.stream();
	}
	public Cab getCabByRegId(String regId) throws CabNotFoundException {
		Optional<Cab> cab = cabs.stream().filter(p -> p.getRegId().equals(regId)).findFirst();
		if(cab.isPresent()) {
			return cab.get();
		}
		throw new CabNotFoundException("Cab with regId:" + regId +"is not present in system.");
	}
}
