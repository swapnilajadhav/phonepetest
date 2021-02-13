package com.phonepe.cabbookingtest.exception;

public class CabBookingNotSupportedInCity extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public CabBookingNotSupportedInCity(String message) {
		super();
		this.message = message;
	}
	
}
