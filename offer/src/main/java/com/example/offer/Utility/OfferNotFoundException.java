package com.example.offer.Utility;

public class OfferNotFoundException extends RuntimeException{

	public OfferNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public OfferNotFoundException(String message) {
		super(message);
	}

	public OfferNotFoundException(Throwable cause) {
		super(cause);
	}

	
}
