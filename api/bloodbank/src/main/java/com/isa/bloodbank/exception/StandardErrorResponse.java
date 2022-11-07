package com.isa.bloodbank.exception;

public class StandardErrorResponse {
	private final String message;
	private final int statusCode;

	public StandardErrorResponse(final BaseException e) {
		this.message = e.getMessage();
		this.statusCode = e.getStatusCode().value();
	}

}
