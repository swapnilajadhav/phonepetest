package com.phonepe.cabbookingtest.exception;

public class CabNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public CabNotFoundException(String message) {
		super();
		this.message = message;
	}
	
}
