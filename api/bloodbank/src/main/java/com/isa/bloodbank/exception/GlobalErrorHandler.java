package com.isa.bloodbank.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<?> handleBaseException(final BaseException e) {
		return new ResponseEntity(new StandardErrorResponse(e), e.getStatusCode());
	}
}
