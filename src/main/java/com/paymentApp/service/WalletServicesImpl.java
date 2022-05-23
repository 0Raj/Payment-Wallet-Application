package com.paymentApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.InsufficientAmountException;
import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.exceptions.UserNotFoundException;
import com.paymentApp.exceptions.WalletNotFoundException;
import com.paymentApp.module.Bank;
import com.paymentApp.module.CurrentUserSession;
import com.paymentApp.module.Customer;
import com.paymentApp.module.FundTransferDTO;
import com.paymentApp.module.Transaction;
import com.paymentApp.module.TransactionType;
import com.paymentApp.module.Wallet;
import com.paymentApp.repository.BankDAO;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.repository.SessionDAO;
import com.paymentApp.repository.TransactionDAO;
import com.paymentApp.repository.WalletDAO;

@Service
public class WalletServicesImpl implements WalletService{

	@Autowired
	private BankDAO bankDAO;
	
	@Autowired
	private WalletDAO walletDAO;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Override
	public Bank addBank(Bank bank) { 
		
		List<CurrentUserSession> list = sessionDAO.findAll();
		Integer id = list.get(0).getCustomerId();
		
		Wallet wallet = customerDAO.getById(id).getWallet();
		
		wallet.setBank(bank);
		bank.setWalletId(wallet.getWalletId());	
		
		walletDAO.save(wallet);
 		
		return  bankDAO.save(bank);
	}

	@Override
	public String removeBank() {
		
		List<CurrentUserSession> list = sessionDAO.findAll();
		Integer id = list.get(0).getCustomerId();
		
		Integer accountNumber = customerDAO.getById(id).getWallet().getBank().getAccountNumber();
		
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
	public double showBankBalance() {
		
		List<CurrentUserSession> list = sessionDAO.findAll();
		Integer id = list.get(0).getCustomerId();
		
		Integer accountNumber = customerDAO.getById(id).getWallet().getBank().getAccountNumber();

		Optional<Bank> optBank = bankDAO.findById(accountNumber);
		
		if(!optBank.isPresent()) {
			throw new NotFoundException("Account is not found");
		}
		
		return optBank.get().getBankBalance();
	}
	
	@Override
	public double showWalletBalance() throws WalletNotFoundException {
		List<CurrentUserSession> list = sessionDAO.findAll();
		Integer id = list.get(0).getCustomerId();
		
		Double balance = customerDAO.getById(id).getWallet().getWalletBalance();
		
		return balance;
	}
	
 //transaction module completed 
	
	@Override
	public String fundTransterFromWalletToWallet(FundTransferDTO fundTransferDTO) {
		
		List<CurrentUserSession> list = sessionDAO.findAll();
		Integer id = list.get(0).getCustomerId();
		
		Customer sourceCustomer = customerDAO.getById(id);
		Wallet sourceWallet = sourceCustomer.getWallet();
		
		if(sourceWallet.getWalletBalance() < fundTransferDTO.getAmount()) {
			throw new InsufficientAmountException("Insufficient balance in wallet");
		}
		
		Optional<Customer> optTargetCustomer = customerDAO.findByMobileNo(fundTransferDTO.getTargetMobileNo());
		
		if(!optTargetCustomer.isPresent()) {
			throw new UserNotFoundException("Target user mobile number not valid");
		}

		Customer targetCustomer = optTargetCustomer.get();
		
		Optional<Wallet> optTargetWallet = walletDAO.findById(targetCustomer.getWallet().getWalletId()); 
		Wallet targetWallet = optTargetWallet.get();
		
		sourceWallet.setWalletBalance(sourceWallet.getWalletBalance() - fundTransferDTO.getAmount());
		targetWallet.setWalletBalance(targetWallet.getWalletBalance() + fundTransferDTO.getAmount());
		
		walletDAO.save(sourceWallet);
		walletDAO.save(targetWallet);
		
		Transaction myTransaction = new Transaction();	
		myTransaction.setAmount(fundTransferDTO.getAmount());
		myTransaction.setTransactionType(TransactionType.WALLET_TO_WALLET_FUND_TRANSFER);
		myTransaction.setTransactionDate(LocalDateTime.now());
	
		
		String description =fundTransferDTO.getAmount()+  " Rupees is transerfered to "+targetCustomer.getMobileNo() + " sucessfully..."; 
		
		myTransaction.setDescription(description);
		myTransaction.setWalletId(sourceWallet.getWalletId());
		
		
		System.out.println(myTransaction.toString());
		
		transactionDAO.save(myTransaction);
		
		return description;
	}
	
	//transaction module completed 

	@Override
	public String depositAmount(Double amount) {
		List<CurrentUserSession> list = sessionDAO.findAll();
		Integer id = list.get(0).getCustomerId();
		
		Wallet wallet = customerDAO.getById(id).getWallet();
		
		if(wallet.getWalletBalance() < amount) {
			throw new InsufficientAmountException("Insufficient amount in wallet");
		}
		
		wallet.setWalletBalance(wallet.getWalletBalance() - amount);
		
		Bank bank = customerDAO.getById(id).getWallet().getBank();
		bank.setBankBalance(bank.getBankBalance() + amount);

		
		bankDAO.save(bank);
		
		Transaction myTransaction = new Transaction();	
		myTransaction.setAmount(amount);
		myTransaction.setTransactionType(TransactionType.WALLET_TO_ACCOUNT);
		myTransaction.setTransactionDate(LocalDateTime.now());

		String description = amount+" is credited to your account";
		
		myTransaction.setDescription(description);
		myTransaction.setWalletId(wallet.getWalletId());
		
		
		System.out.println(myTransaction.toString());
		
		transactionDAO.save(myTransaction);
		
		return description;
		
	}
	

	@Override
	public String addMoney(Double amount) {
		List<CurrentUserSession> list = sessionDAO.findAll();
		Integer id = list.get(0).getCustomerId();
		
		Customer customer = customerDAO.getById(id);
		Wallet wallet = customer.getWallet();
		Bank bank = customer.getWallet().getBank();
		
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
