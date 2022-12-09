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
	public ResponseEntity<?> handleRuntimeException(final RuntimeException e) {
		return new ResponseEntity<>(new StandardErrorResponse(new BaseException(e.getMessage(), HttpStatus.BAD_REQUEST)), HttpStatus.BAD_REQUEST);
	}

}
