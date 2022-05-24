package com.paymentApp.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	// for user Login
	@PostMapping(value = "/login")
	public String logInCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
		return customerLogIn.logIntoAccount(customerDTO);
	}
	
	// for user Logout
	@PatchMapping(value = "/logout/{key}")
	public String logOutCustomer(@PathVariable("key") String key) {
		return customerLogIn.logOutFromAccount(key);
	}

}
