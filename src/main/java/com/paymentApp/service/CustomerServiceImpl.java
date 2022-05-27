package com.paymentApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.UserAlreadyExistWithMobileNumber;
import com.paymentApp.module.Customer;
import com.paymentApp.module.Wallet;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.util.GetCurrentLoginUserSessionDetailsImpl;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private GetCurrentLoginUserSessionDetailsImpl getCurrentLoginUser;
	
	@Override
	public Customer createCustomer(Customer customer) {

		Optional<Customer> opt = customerDAO.findByMobileNo(customer.getMobileNo());
		
		if(opt.isPresent()) {
			throw new UserAlreadyExistWithMobileNumber("User already exist with this mobile number");
		}
		
		Wallet wallet = new Wallet();
		wallet.setWalletBalance(0.0);
		wallet.setCustomer(customer);
		
		customer.setWallet(wallet);
		
		return  customerDAO.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer, String key) {
		
		Customer customer2 = getCurrentLoginUser.getCurrentCustomer(key);
		
		if(customer2 == null) {
			customerDAO.save(customer);
		}
		return customer;
	}

	@Override
	public Customer deleteCustomer(String key) {
		
		Customer customer = getCurrentLoginUser.getCurrentCustomer(key);		
		customerDAO.delete(customer);
		return customer;
	}

	@Override
	public Customer getCustomerDetails(String key) {
		
		Customer customer = getCurrentLoginUser.getCurrentCustomer(key);
		
		return customer;
	}

}
