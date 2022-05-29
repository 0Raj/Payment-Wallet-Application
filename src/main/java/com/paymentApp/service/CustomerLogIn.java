package com.paymentApp.service;

import com.paymentApp.module.CustomerDTO;

public interface CustomerLogIn {
	
	public String logIntoAccount(CustomerDTO customerDTO);
	
	public String logOutFromAccount(String key);

}
