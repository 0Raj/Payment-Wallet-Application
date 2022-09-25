package com.paymentApp.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.module.Bank;
import com.paymentApp.module.Customer;
import com.paymentApp.module.Wallet;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.security.SecurityCustomer;

import lombok.Data;

@Data
@Service
public class GetCurrentLoginUserDetails {

	private Object principal;

	@Autowired
	private CustomerDAO customerDAO;

	public boolean checkLogin() {
		
		principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return true;
		}

		return false;

	}

	public Customer getCurrentCustomer() {
		if (checkLogin()) {
			String username = ((UserDetails) principal).getUsername();
			SecurityCustomer securityCustomer = (SecurityCustomer) principal;

			Optional<Customer> currentCustomer = customerDAO.findByMobileNo(securityCustomer.getMobileNumber());
			return currentCustomer.get();
		} else {
			throw new NotFoundException("User Not Found Please Login");
		}

	}

	public Wallet getCurrentUserWallet() {

		Customer customer = getCurrentCustomer();

		Wallet wallet = customer.getWallet();

		return wallet;
	}

	public Bank getCurrentUserBank() {

		Customer customer = getCurrentCustomer();

		Bank bank = customer.getWallet().getBank();

		return bank;
	}

}
