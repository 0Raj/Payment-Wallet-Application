package com.paymentApp.service;

import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.module.Customer;

public interface CustomerService {
	
	public Customer createCustomer(Customer customer);
	
	public Customer updateCustomer(Customer customer, String key) throws NotFoundException;
	
	public Customer deleteCustomer(String key) throws NotFoundException;
	
	public Customer getCustomerDetails(String key) throws NotFoundException;
	
}
