package com.paymentApp.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymentApp.module.Customer;
import com.paymentApp.module.CustomerDTO;
import com.paymentApp.service.CustomerServiceImpl;
import com.paymentApp.service.CustomerLogIn;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	@Autowired
	private CustomerLogIn customerLogIn;
	
	// to register user
	@PostMapping(value = "/customer")
	public Customer saveCustomer(@Valid @RequestBody Customer customer) {
		
		return customerServiceImpl.createCustomer(customer);
	}
	
	
	// for user verification
	@PostMapping(value = "/login")
	public String logInCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
		return customerLogIn.logIntoAccount(customerDTO);
	}
	
	// for user verification
		@PatchMapping(value = "/logout")
		public String logOutCustomer() {
			return customerLogIn.logOutFromAccount();
		}

}
