package com.paymentApp.service;

import com.paymentApp.module.CustomerDTO;

public interface CustomerValidation {
	
	public String logIntoAccount(CustomerDTO customerDTO);
	
	public String logOutFromAccount();

}
