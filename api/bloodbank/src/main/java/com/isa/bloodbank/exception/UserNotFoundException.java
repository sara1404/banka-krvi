package com.isa.bloodbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Could not find user with given ID!")
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException() { super(); }
}
