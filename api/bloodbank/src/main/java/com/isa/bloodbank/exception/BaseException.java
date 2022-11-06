package com.isa.bloodbank.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BaseException extends Exception {

	private final HttpStatus statusCode;

	public BaseException(final String message, final HttpStatus statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

}

