package com.isa.bloodbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<?> handleBaseException(final BaseException e) {
		return new ResponseEntity(new StandardErrorResponse(e), e.getStatusCode());
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleSqlException(final RuntimeException e) throws BaseException {
		System.out.println("Triggered");
		final BaseException exc = new BaseException("Unique constraint violated!", HttpStatus.BAD_REQUEST);
		throw exc;
		//return new ResponseEntity<>(new StandardErrorResponse(exc), HttpStatus.BAD_REQUEST);
	}

}
