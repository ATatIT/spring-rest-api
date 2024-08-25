package com.projectStruct.exceptionhandeling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Implementing Exception Handling - 404 Resource Not Found
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String massage) {
		super(massage);
		// TODO Auto-generated constructor stub
	}
	
}
