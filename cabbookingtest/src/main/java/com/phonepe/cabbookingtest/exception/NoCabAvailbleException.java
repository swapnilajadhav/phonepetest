package com.phonepe.cabbookingtest.exception;

public class NoCabAvailbleException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public NoCabAvailbleException(String string) {
		message = string;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "NoCabAvailbleException [message=" + message + "]";
	}
	
}
