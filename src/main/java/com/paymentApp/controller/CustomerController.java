package com.paymentApp.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
=======
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentApp.module.Customer;
import com.paymentApp.service.CustomerServiceImpl;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	// to register user
	@PostMapping(value = "/customer")
	public Customer saveCustomer(@Valid @RequestBody Customer customer) {
		return customerServiceImpl.createCustomer(customer);
	}
	
	// To update existing user details by passing its login key
	@PutMapping(value = "/customer")
	public Customer updateCustomer(@Valid @RequestBody Customer customer, @RequestParam(required = false) String key) {
		return customerServiceImpl.updateCustomer(customer, key);
	}
	
	// To delete existing user details by passing its login key
	@DeleteMapping(value = "/customer")
	public Customer deleteCustomer(@RequestParam(required = false) String key) {
		return customerServiceImpl.deleteCustomer(key);
	}
	
<<<<<<< Updated upstream
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

=======
	// To update existing user details by passing its login key
	@GetMapping(value = "/customer")
	public Customer getCustomerDetails(@RequestParam(required = false) String key) {
		return customerServiceImpl.getCustomerDetails(key);
	}
	
>>>>>>> Stashed changes
}
