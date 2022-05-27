package com.paymentApp.service;

import java.time.LocalDateTime;
<<<<<<< Updated upstream
import java.util.Optional;
import java.util.UUID;
=======
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
import com.paymentApp.util.GetCurrentLoginUserSessionDetails;
=======
import com.paymentApp.util.GetCurrentLoginUserSessionDetailsImpl;

import net.bytebuddy.utility.RandomString;
>>>>>>> Stashed changes

@Service
public class CustomerLogInImpl implements CustomerLogIn{

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
<<<<<<< Updated upstream
	private GetCurrentLoginUserSessionDetails getCurrentLoginUser;
=======
	private GetCurrentLoginUserSessionDetailsImpl getCurrentLoginUser;
>>>>>>> Stashed changes
	
	@Override
	public String logIntoAccount(CustomerDTO customerDTO) {
		
		Optional<Customer> opt = customerDAO.findByMobileNo(customerDTO.getMobileNo());
		Customer newCustomer = opt.get();
		
		Integer customerId = newCustomer.getCustomerId();
		
		Optional<CurrentUserSession> currentUserOptional = sessionDAO.findByCustomerId(customerId);
		
		if(!opt.isPresent()) {
			throw new NotFoundException("Please Enter Valid Mobile Number");
		}
		
		if(currentUserOptional.isPresent()) {
			throw new UserAlreadyExistWithMobileNumber("User already logged in with this number");
		}
		
		if(newCustomer.getPassword().equals(customerDTO.getPassword())) {
			String key = UUID.randomUUID().toString();
			
<<<<<<< Updated upstream
=======
			String key = RandomString.make(6);
			
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
		
		Optional<CurrentUserSession> currentUserOptional = sessionDAO.findByUuid(key);
		
		if(!currentUserOptional.isPresent()) {
			throw new NotFoundException("User is not logged in with this number");
		}
>>>>>>> Stashed changes
		
		CurrentUserSession currentUserSession = getCurrentLoginUser.getCurrentUserSession(key);
		sessionDAO.delete(currentUserSession);
		
		return "Logged Out...";
	}

}
