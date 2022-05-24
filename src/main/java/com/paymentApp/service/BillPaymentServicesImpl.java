package com.paymentApp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.InsufficientAmountException;
import com.paymentApp.module.BillPayment;
import com.paymentApp.module.BillType;
import com.paymentApp.module.CurrentUserSession;
import com.paymentApp.module.Customer;
import com.paymentApp.module.Transaction;
import com.paymentApp.module.TransactionType;
import com.paymentApp.module.Wallet;
import com.paymentApp.repository.BillPaymentDAO;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.repository.SessionDAO;
import com.paymentApp.repository.TransactionDAO;
import com.paymentApp.repository.WalletDAO;

@Service
public class BillPaymentServicesImpl implements BillPaymentServices{

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private BillPaymentDAO billPaymentDAO;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private WalletDAO walletDAO;
	
	@Autowired
	private TransactionDAO transactionDAO;

// to get wallet of customer which is currently logged in;
	private Wallet getCurrentCustomersWallet() {
		
		List<CurrentUserSession> list = sessionDAO.findAll();
		
		CurrentUserSession currentUserSession =  list.get(0);
		
		Integer id = currentUserSession.getCustomerId();
		
		Customer customer = customerDAO.getById(id);
		
		Wallet wallet = customer.getWallet();
		
		return wallet;
		
	}
		
	
	@Override
	public String electricityBillPayment() {
		
		Wallet wallet = getCurrentCustomersWallet();
		
		double amount = 50*((int) Math.random()+1);
		
		if(wallet.getWalletBalance() <= amount) {
			throw new InsufficientAmountException("Insufficient amount in wallet");
		}
		
		BillPayment billPayment2 = new BillPayment(BillType.ELECTRICITY_BILL, amount,LocalDateTime.now(), wallet.getWalletId());
		
		wallet.setWalletBalance(wallet.getWalletBalance() - amount);
		
		Transaction myTransaction = new Transaction(TransactionType.WALLET_TO_ELECTRICITY_BILL, LocalDateTime.now(), amount, "Electricity bill is paid from wallet", wallet.getWalletId());
		
		transactionDAO.save(myTransaction);
		
		walletDAO.save(wallet);
		
		billPaymentDAO.save(billPayment2);

		return "Electricity bill of Rs : " + amount + " is paid successfully";
	}

	@Override
	public String mobileRechargeBillPayment() {

		Wallet wallet = getCurrentCustomersWallet();
		
		double amount = 50*((int) Math.random()+1);
		
		if(wallet.getWalletBalance() < amount) {
			throw new InsufficientAmountException("Insufficient amount in wallet");
		}
		
		BillPayment billPayment2 = new BillPayment(BillType.MOBILE_RECHARGE, amount,LocalDateTime.now(), wallet.getWalletId());
		wallet.setWalletBalance(wallet.getWalletBalance() - amount);
		
		Transaction myTransaction = new Transaction(TransactionType.WALLET_TO_MOBILE_RECHARGE, LocalDateTime.now(), amount, "Mobile is recharged from wallet", wallet.getWalletId());
		
		transactionDAO.save(myTransaction);
		
		walletDAO.save(wallet);
		
		billPaymentDAO.save(billPayment2);

		return "Mobile recharge of Rs : " + amount + " is done successfully";
	}

	@Override
	public List<BillPayment> viewBillPayment() {

		Wallet wallet = getCurrentCustomersWallet();
		
		List<BillPayment> list2 = billPaymentDAO.findAllBillPaymentsByWalletId(wallet.getWalletId());
		
		return list2;
	}
	
}
