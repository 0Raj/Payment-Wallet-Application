package com.paymentApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.exceptions.UserAlreadyExistWithMobileNumber;
import com.paymentApp.module.Customer;
import com.paymentApp.module.Wallet;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.security.SecurityCustomer;
import com.paymentApp.util.GetCurrentLoginUserDetails;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private GetCurrentLoginUserDetails getCurrentLoginUserDetails;

	@Override
	public Customer createCustomer(Customer customer) {

		Optional<Customer> opt = customerDAO.findByMobileNo(customer.getMobileNo());

		if (opt.isPresent()) {
			throw new UserAlreadyExistWithMobileNumber("User already exist with this mobile number");
		}

		Wallet wallet = new Wallet();
		wallet.setWalletBalance(0.0);
		wallet.setCustomer(customer);

		customer.setWallet(wallet);
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));

		return customerDAO.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer) {

		if (getCurrentLoginUserDetails.checkLogin()) {
			customerDAO.save(customer);
			return customer;
		} else {
			throw new NotFoundException("No user found.. try login first");
		}

	}

	@Override
	public Customer deleteCustomer() {

		if (getCurrentLoginUserDetails.checkLogin()) {
			Customer customer = getCurrentLoginUserDetails.getCurrentCustomer();
			System.out.println("Before");
			customerDAO.delete(customer);
			System.out.println("Before");
			return customer;

		} else {
			throw new NotFoundException("No user found.. try login first");
		}

	}

	@Override
	public Customer getCustomerDetails() {

		if (getCurrentLoginUserDetails.checkLogin()) {

			return getCurrentLoginUserDetails.getCurrentCustomer();

		} else {
			throw new NotFoundException("No user found.. try login first");
		}

	}

}
