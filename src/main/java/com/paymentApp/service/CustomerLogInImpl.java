package com.paymentApp.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.InvalidPasswordException;
import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.exceptions.UserAlreadyExistWithMobileNumber;
import com.paymentApp.module.CurrentUserSession;
import com.paymentApp.module.Customer;
import com.paymentApp.module.CustomerDTO;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.repository.SessionDAO;
import com.paymentApp.util.GetCurrentLoginUserSessionDetailsImpl;

import net.bytebuddy.utility.RandomString;

@Service
public class CustomerLogInImpl implements CustomerLogIn{

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private GetCurrentLoginUserSessionDetailsImpl getCurrentLoginUser;
	
	@Override
	public String logIntoAccount(CustomerDTO customerDTO) {
		
		Optional<Customer> opt = customerDAO.findByMobileNo(customerDTO.getMobileNo());
		Customer newCustomer = opt.get();
		
		Integer customerId = newCustomer.getCustomerId();
		
		Optional<CurrentUserSession> currentUserOptional = sessionDAO.findByCustomerId(customerId);
		
		if(!opt.isPresent()) {
			throw new NotFoundException("Please Enter Valid Mobile Number");
		}
		
		Customer newCustomer = opt.get();
		Integer customerId = newCustomer.getCustomerId();
		Optional<CurrentUserSession> currentUserOptional = sessionDAO.findByCustomerId(customerId);
		
		if(currentUserOptional.isPresent()) {
			throw new UserAlreadyExistWithMobileNumber("User already logged in with this number");
		}
		
		if(newCustomer.getPassword().equals(customerDTO.getPassword())) {
			
			String key = RandomString.make(6);
			
			CurrentUserSession currentUserSession = new CurrentUserSession(newCustomer.getCustomerId(), key, LocalDateTime.now());			
			sessionDAO.save(currentUserSession);

			return currentUserSession.toString();
		}
		else {
			throw new InvalidPasswordException("Please Enter Valid Password");
		}
	}

	@Override
	public String logOutFromAccount(String key) {
		
		Optional<CurrentUserSession> currentUserOptional = sessionDAO.findByUuid(key);
		
		if(!currentUserOptional.isPresent()) {
			throw new NotFoundException("User is not logged in with this number");
		}
		
		CurrentUserSession currentUserSession = getCurrentLoginUser.getCurrentUserSession(key);
		sessionDAO.delete(currentUserSession);
		
		return "Logged Out...";
	}

}
