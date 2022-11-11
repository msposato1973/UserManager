package com.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceNotFoundException.class);

	
	private String resourceName;
    private String fieldName;
    private long fieldValue;

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue)); // Post not found with id : 1
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

	public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldValue() {
        return fieldValue;
    }
}
