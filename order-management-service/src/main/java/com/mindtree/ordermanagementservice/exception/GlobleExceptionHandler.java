package com.mindtree.ordermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mindtree.ordermanagementservice.model.ResponseStatusModel;

@RestControllerAdvice
public class GlobleExceptionHandler {
	@ExceptionHandler(OrderManagementServiceException.class)
	public ResponseEntity<ResponseStatusModel> customException(OrderManagementServiceException e) {
		return new ResponseEntity<>(new ResponseStatusModel(404, "failed", e.getMessage(), null), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseStatusModel> incorrectJSONFormat() {
		return new ResponseEntity<>(new ResponseStatusModel(400, "failed", "Incorrect JSON format", null),
				HttpStatus.BAD_REQUEST);
	}
}
