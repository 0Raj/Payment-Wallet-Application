package com.paymentApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.exceptions.UserAlreadyExistWithMobileNumber;
import com.paymentApp.exceptions.UserNotFoundException;
import com.paymentApp.module.CurrentUserSession;
import com.paymentApp.module.Customer;
import com.paymentApp.module.Wallet;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.repository.SessionDAO;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private SessionDAO sessionDAO;
	
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
	public Customer updateCustomer(Customer customer) throws NotFoundException {
		
		Optional<Customer> optional = customerDAO.findById(customer.getCustomerId());
		
		if(optional.isPresent()) {
			customerDAO.save(customer);
		}
		else {
			throw new NotFoundException("User Does not exist");
		}
		
		return customer;
	}

	@Override
	public Customer deleteCustomer() {
		
		List<CurrentUserSession> list = sessionDAO.findAll();
		
		CurrentUserSession currentUserSession =  list.get(0);
		
		Integer id = currentUserSession.getCustomerId();
		
		Optional<Customer> customer = customerDAO.findById(id) ;
		
		customerDAO.delete(customer.get());
		
		return customer.get();
	}

	@Override
	public Customer getCustomerDetails() {
		
		List<CurrentUserSession> list = sessionDAO.findAll();
		
		CurrentUserSession currentUserSession =  list.get(0);
		
		Integer id = currentUserSession.getCustomerId();
		
		Optional<Customer> customer = customerDAO.findById(id) ;
		
		return customer.get();
	}

}
