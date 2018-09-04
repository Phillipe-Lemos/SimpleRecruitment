package com.recruitment.simplerecruitment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalStateChangeException extends RuntimeException {

	private static final long serialVersionUID = -9077542943346546191L;

	public IllegalStateChangeException(String message) {
		super(message);
	}
}
