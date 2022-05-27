package com.paymentApp.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymentApp.exceptions.InsufficientAmountException;
import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.module.Bank;
import com.paymentApp.module.Customer;
import com.paymentApp.module.FundTransferDTO;
import com.paymentApp.module.Transaction;
import com.paymentApp.module.TransactionType;
import com.paymentApp.module.Wallet;
import com.paymentApp.repository.BankDAO;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.repository.TransactionDAO;
import com.paymentApp.repository.WalletDAO;
import com.paymentApp.util.GetCurrentLoginUserSessionDetailsImpl;

@Service
public class WalletServicesImpl implements WalletService{

	@Autowired
	private BankDAO bankDAO;
	
	@Autowired
	private WalletDAO walletDAO;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private GetCurrentLoginUserSessionDetailsImpl getCurrentLoginUser;
		
	@Override
	public Bank addBank(Bank bank, String key) { 
		
		Wallet wallet = getCurrentLoginUser.getCurrentUserWallet(key);
		wallet.setBank(bank);
		
		bank.setWalletId(wallet.getWalletId());	
		walletDAO.save(wallet);
 		
		return  bankDAO.save(bank);
	}

	@Override
	public String removeBank(String key) {

		Integer accountNumber = getCurrentLoginUser.getCurrentUserWallet(key).getBank().getAccountNumber();
		
		Optional<Bank> optBank = bankDAO.findById(accountNumber);
		
		if(!optBank.isPresent()) {
			throw new NotFoundException("Account is not found");
		}
		
		Optional<Wallet> optional = walletDAO.findById(optBank.get().getWalletId());
		
		Wallet wallet = optional.get();
		
		wallet.setBank(null);
		
		walletDAO.save(wallet);
 		
		bankDAO.delete(optBank.get());
		
		return  "Bank is deleted sucessfully";
	}

	@Override
	public double showBankBalance(String key) {
		
		Integer accountNumber = getCurrentLoginUser.getCurrentUserWallet(key).getBank().getAccountNumber();

		Optional<Bank> optBank = bankDAO.findById(accountNumber);
		
		if(!optBank.isPresent()) {
			throw new NotFoundException("Account is not found");
		}
		
		return optBank.get().getBankBalance();
	}
	
	@Override
	public double showWalletBalance(String key) throws NotFoundException {

		Double balance = getCurrentLoginUser.getCurrentUserWallet(key).getWalletBalance();
		
		return balance;
	}
	
	
 //transaction module completed 
	@Override
	@Transactional
	public String fundTransterFromWalletToWallet(FundTransferDTO fundTransferDTO, String key) {
		
		Wallet sourceWallet = getCurrentLoginUser.getCurrentUserWallet(key);
		
		if(sourceWallet.getWalletBalance() < fundTransferDTO.getAmount()) {
			throw new InsufficientAmountException("Insufficient balance in wallet");
		}
		
		Optional<Customer> optTargetCustomer = customerDAO.findByMobileNo(fundTransferDTO.getTargetMobileNo());
		
		if(!optTargetCustomer.isPresent()) {
			throw new NotFoundException("Target user mobile number not valid");
		}

		Customer targetCustomer = optTargetCustomer.get();
		
		Optional<Wallet> optTargetWallet = walletDAO.findById(targetCustomer.getWallet().getWalletId()); 
		Wallet targetWallet = optTargetWallet.get();
		
		sourceWallet.setWalletBalance(sourceWallet.getWalletBalance() - fundTransferDTO.getAmount());
		targetWallet.setWalletBalance(targetWallet.getWalletBalance() + fundTransferDTO.getAmount());
		
		walletDAO.save(sourceWallet);
		walletDAO.save(targetWallet);
		
		String description =fundTransferDTO.getAmount()+  " Rupees is transerfered to "+targetCustomer.getMobileNo() + " sucessfully..."; 
		
		Transaction myTransaction = new Transaction(TransactionType.WALLET_TO_WALLET_FUND_TRANSFER, LocalDateTime.now(), fundTransferDTO.getAmount(), description, sourceWallet.getWalletId());
		
		transactionDAO.save(myTransaction);
		
		return description;
	}

	@Override
	@Transactional
	public String depositAmount(Double amount, String key) {
		
		Wallet wallet = getCurrentLoginUser.getCurrentUserWallet(key);
		
		if(wallet.getWalletBalance() < amount) {
			throw new InsufficientAmountException("Insufficient amount in wallet");
		}
		
		wallet.setWalletBalance(wallet.getWalletBalance() - amount);
		
		Bank bank = wallet.getBank();
		
		bank.setBankBalance(bank.getBankBalance() + amount);

		bankDAO.save(bank);
		
		String description = amount+" is credited to your account";
		
		Transaction myTransaction = new Transaction(TransactionType.WALLET_TO_ACCOUNT, LocalDateTime.now(),amount, description, wallet.getWalletId());
		
		transactionDAO.save(myTransaction);
		
		return description;
		
	}
	

	@Override
	@Transactional
	public String addMoney(Double amount, String key) {
		
		Wallet wallet = getCurrentLoginUser.getCurrentUserWallet(key);
		
		Bank bank = wallet.getBank();
		
		if(bank == null) {
			throw new NotFoundException("No bank is linked with this account");
		}
		
		if(bank.getBankBalance() < amount) {
			throw new InsufficientAmountException("Insufficient amount in your bank");
		}
		
		bank.setBankBalance(bank.getBankBalance() - amount);
		
		wallet.setWalletBalance(wallet.getWalletBalance() + amount);
		
		bankDAO.save(bank);
		walletDAO.save(wallet);	
		
		Transaction myTransaction = new Transaction(TransactionType.WALLET_TO_ACCOUNT, LocalDateTime.now(), amount, amount+" is credited to your wallet", wallet.getWalletId());
		
		transactionDAO.save(myTransaction);
		
		return amount+" is credited to your wallet";
	}
}
