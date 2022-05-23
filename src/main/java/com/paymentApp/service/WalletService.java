package com.paymentApp.service;

import com.paymentApp.exceptions.InsufficientAmountException;
import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.module.Bank;
import com.paymentApp.module.FundTransferDTO;


public interface WalletService {
	
	public Bank addBank(Bank bank) ;
	
	public String removeBank() throws NotFoundException;
	
	public double showBankBalance() throws NotFoundException;
	
	public double showWalletBalance() throws NotFoundException;
	
	public String fundTransterFromWalletToWallet(FundTransferDTO fundTransferDTO) throws InsufficientAmountException;
	
	public String depositAmount(Double amount) throws InsufficientAmountException;
	
	public String addMoney(Double amount) throws InsufficientAmountException;
	
	
}
