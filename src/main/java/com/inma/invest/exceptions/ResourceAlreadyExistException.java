package com.inma.invest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class ResourceAlreadyExistException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private Object fieldValue;

	public ResourceAlreadyExistException(String resourceName, Object fieldValue) {
		super(String.format("This %s Already Exist with : '%s'", resourceName, fieldValue));
		this.resourceName = resourceName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	
	public Object getFieldValue() {
		return fieldValue;
	}
}
