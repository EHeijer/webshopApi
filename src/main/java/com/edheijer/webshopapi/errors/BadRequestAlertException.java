package com.edheijer.webshopapi.errors;

public class BadRequestAlertException extends Exception{

	public BadRequestAlertException(String errorMessage) {
        super(errorMessage);
    }
}
