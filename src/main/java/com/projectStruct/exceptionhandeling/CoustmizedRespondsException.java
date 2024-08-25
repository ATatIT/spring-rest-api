package com.projectStruct.exceptionhandeling;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.projectStruct.bean.CoustmizedExceptionBean;

@ControllerAdvice
public class CoustmizedRespondsException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<CoustmizedExceptionBean> handleAllException(Exception ex, WebRequest request)
			throws Exception {
		CoustmizedExceptionBean error = new CoustmizedExceptionBean(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<CoustmizedExceptionBean>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<CoustmizedExceptionBean> handleUserNotFoundException(Exception ex, WebRequest request)
			throws Exception {
		CoustmizedExceptionBean error = new CoustmizedExceptionBean(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<CoustmizedExceptionBean>(error, HttpStatus.NOT_FOUND);
	}

	// customized exception  for validation 
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		CoustmizedExceptionBean error = new CoustmizedExceptionBean(LocalDateTime.now(), "Total eroor : " + ex.getErrorCount()+ " & First is : " + ex.getFieldError().getDefaultMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
