package com.paymentApp.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.paymentApp.module.Customer;
import com.paymentApp.module.CustomerDTO;
import com.paymentApp.service.CustomerServiceImpl;
import com.paymentApp.service.CustomerValidation;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	@Autowired
	private CustomerValidation customerValidation;
	
	// to register user
	@PostMapping(value = "/customer")
	public Customer saveCustomer(@Valid @RequestBody Customer customer) {
		
		return customerServiceImpl.createCustomer(customer);
	}
	
//	for front end
//	@GetMapping("/register")
//	public String showForm(Model model) {
//		Customer customer = new Customer();
//		model.addAttribute("customer", customer);
//		
//		return "register";
//	}
//	
//	@PostMapping("/register")
//	public String submitForm(@ModelAttribute("customer") Customer customer){
//		System.out.println(customer);
//		return "registrationSucessfull";
//	}
	
	
	// for user verification
	@PostMapping(value = "/login")
	public String logInCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
		return customerValidation.logIntoAccount(customerDTO);
	}
	
	// for user verification
		@PatchMapping(value = "/logout")
		public String logOutCustomer() {
			return customerValidation.logOutFromAccount();
		}

}
