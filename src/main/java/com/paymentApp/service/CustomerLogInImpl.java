package com.paymentApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.InvalidPasswordException;
import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.module.CurrentUserSession;
import com.paymentApp.module.Customer;
import com.paymentApp.module.CustomerDTO;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.repository.SessionDAO;

@Service
public class CustomerLogInImpl implements CustomerLogIn{

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Override
	public String logIntoAccount(CustomerDTO customerDTO) {
		
		Optional<Customer> opt = customerDAO.findByMobileNo(customerDTO.getMobileNo());
		
		if(!opt.isPresent()) {
			throw new NotFoundException("Please Enter Valid Mobile Number");
		}
		Customer newCustomer = opt.get();
		if(newCustomer.getPassword().equals(customerDTO.getPassword())) {
			
			CurrentUserSession currentUserSession = new CurrentUserSession(); 
			
			currentUserSession.setCustomerId(newCustomer.getCustomerId());
		
			// or make it for 2 user
			sessionDAO.save(currentUserSession);
			
			return "Log in Sucessfull";
		}
		else {
			throw new InvalidPasswordException("Please Enter Valid Password");
		}
	}

	@Override
	public String logOutFromAccount() {
		
		sessionDAO.deleteAll();
		
		return "Logged Out...";
	}

}
