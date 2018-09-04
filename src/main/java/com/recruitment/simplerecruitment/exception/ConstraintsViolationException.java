package com.recruitment.simplerecruitment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Some constraints were reached...")
public class ConstraintsViolationException extends Exception {

	private static final long serialVersionUID = -3387516993224229948L;

	public ConstraintsViolationException(String message) {
		super(message);
	}

}
