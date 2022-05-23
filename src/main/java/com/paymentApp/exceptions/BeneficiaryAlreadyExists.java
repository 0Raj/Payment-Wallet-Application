package com.paymentApp.exceptions;


public class BeneficiaryAlreadyExists extends RuntimeException{

	public BeneficiaryAlreadyExists() {
		
	}
	
	public BeneficiaryAlreadyExists(String message) {
		super(message);
	}
}