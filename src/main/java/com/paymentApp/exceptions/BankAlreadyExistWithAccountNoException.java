package com.paymentApp.exceptions;

public class BankAlreadyExistWithAccountNoException extends RuntimeException{

	public BankAlreadyExistWithAccountNoException() {
		
	}
	
	public BankAlreadyExistWithAccountNoException(String message) {
		super(message);
	}
}
