package com.paymentApp.service;

import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.module.Customer;

public interface CustomerService {
	
	public Customer createCustomer(Customer customer);
	
	public Customer updateCustomer(Customer customer) throws NotFoundException;
	
	public Customer deleteCustomer() throws NotFoundException;
	
	public Customer getCustomerDetails() throws NotFoundException;
	
}
