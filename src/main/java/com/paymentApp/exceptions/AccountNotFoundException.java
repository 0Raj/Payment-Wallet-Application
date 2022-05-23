package com.paymentApp.exceptions;

public class AccountNotFoundException extends RuntimeException{
public AccountNotFoundException() {
		
	}

	public AccountNotFoundException(String message){
		super(message);
	}
}
