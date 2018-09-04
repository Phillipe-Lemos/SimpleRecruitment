package com.recruitment.simplerecruitment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConfictException extends Exception {

	private static final long serialVersionUID = -6300728315892883483L;

	public ConfictException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
