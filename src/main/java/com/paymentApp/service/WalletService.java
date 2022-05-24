package com.paymentApp.service;

import com.paymentApp.exceptions.InsufficientAmountException;
import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.module.Bank;
import com.paymentApp.module.FundTransferDTO;


public interface WalletService {
	
	public Bank addBank(Bank bank, String key) ;
	
	public String removeBank(String key) throws NotFoundException;
	
	public double showBankBalance(String key) throws NotFoundException;
	
	public double showWalletBalance(String key) throws NotFoundException;
	
	public String fundTransterFromWalletToWallet(FundTransferDTO fundTransferDTO, String key) throws InsufficientAmountException;
	
	public String depositAmount(Double amount, String key) throws InsufficientAmountException;
	
	public String addMoney(Double amount, String key) throws InsufficientAmountException;
	
	
}
