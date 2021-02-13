package com.phonepe.cabbookingtest.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.phonepe.cabbookingtest.exception.CabBookingNotSupportedInCity;
import com.phonepe.cabbookingtest.exception.CabNotFoundException;
import com.phonepe.cabbookingtest.exception.IllegalCabStatusException;
import com.phonepe.cabbookingtest.exception.NoCabAvailbleException;

@ControllerAdvice
public class CabApplicatoinExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({CabNotFoundException.class,CabBookingNotSupportedInCity.class,NoCabAvailbleException.class,IllegalCabStatusException.class})
    public ResponseEntity<Object> handleNodataFoundException(
    		Exception ex, WebRequest request) {
		
        ApiError apiErr = new ApiError();
        apiErr.setMessage(ex.getMessage());
        ResponseEntity<Object> res = new ResponseEntity<>(apiErr, HttpStatus.FAILED_DEPENDENCY);
        return res;
    }
}
