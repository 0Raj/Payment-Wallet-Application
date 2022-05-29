package com.paymentApp.util;

import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.module.Bank;
import com.paymentApp.module.CurrentUserSession;
import com.paymentApp.module.Customer;
import com.paymentApp.module.Wallet;

public interface GetCurrentLoginUserSessionDetailsInerface {

	public CurrentUserSession getCurrentUserSession(String key) throws NotFoundException;
	
	public Integer getCurrentUserSessionId(String key) throws NotFoundException;
	
	public Customer getCurrentCustomer(String key) throws NotFoundException;
	
	public Wallet getCurrentUserWallet(String key) throws NotFoundException;
	
	public Bank getCurrentUserBank(String key) throws NotFoundException;
}
