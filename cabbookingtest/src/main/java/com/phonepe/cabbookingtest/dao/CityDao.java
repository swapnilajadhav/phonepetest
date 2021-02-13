package com.phonepe.cabbookingtest.dao;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.phonepe.cabbookingtest.vo.City;

@Component
public class CityDao {
	private Set<City> cities = new HashSet<>();
	
	public boolean addCity(City city) {
		return cities.add(city);
	}
	public boolean removeCity(City city) {
		return cities.remove(city);
	}	
	
	public Stream<City> getCityStream(){
		return cities.stream();
	}
	public boolean isCityOnboarded(City city) {
		return cities.contains(city);
	}
}
