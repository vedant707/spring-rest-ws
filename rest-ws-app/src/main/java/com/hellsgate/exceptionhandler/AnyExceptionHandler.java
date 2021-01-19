package com.hellsgate.exceptionhandler;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hellsgate.response.model.ErrorResponse;

/* EXCEPTIONS HANDLING -
 * 1. Handle an exception
 * 2. Return custom error message object
 * 3. Handle a specific exception 
 * 4. Throw and handle custom exception 
 * 5. Catch more than one exception using one method */

@ControllerAdvice
public class AnyExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception ex,WebRequest request){
		
		ErrorResponse errresp = new ErrorResponse(new Date(), ex.toString());
		
		return new ResponseEntity<>(errresp, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {NullPointerException.class, CustomException.class})
	public ResponseEntity<Object> handleNullException(Exception ex,WebRequest request){
		
		ErrorResponse errresp = new ErrorResponse(new Date(), "specific exception handler - "+ex.toString());
		
		return new ResponseEntity<>(errresp, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*@ExceptionHandler(value = {CustomException.class})
	public ResponseEntity<Object> handleCustomException(CustomException custex,WebRequest request){
		
		ErrorResponse errresp = new ErrorResponse(new Date(), "custom exception handler - "+custex.toString());
		
		return new ResponseEntity<>(errresp, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
	
}
