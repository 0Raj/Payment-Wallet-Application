package com.paymentApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.InvalidPasswordException;
import com.paymentApp.exceptions.UserNotFoundException;
import com.paymentApp.module.CurrentUserSession;
import com.paymentApp.module.Customer;
import com.paymentApp.module.CustomerDTO;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.repository.SessionDAO;

@Service
public class CustomerValidationImpl implements CustomerValidation{

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Override
	public String logIntoAccount(CustomerDTO customerDTO) {
		
		Optional<Customer> opt = customerDAO.findByMobileNo(customerDTO.getMobileNo());
		
		if(!opt.isPresent()) {
			throw new UserNotFoundException("Please Enter Valid Mobile Number");
		}
		Customer newCustomer = opt.get();
		if(newCustomer.getPassword().equals(customerDTO.getPassword())) {
			
			CurrentUserSession currentUserSession = new CurrentUserSession();
			
			currentUserSession.setCustomerId(newCustomer.getCustomerId());
			
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
