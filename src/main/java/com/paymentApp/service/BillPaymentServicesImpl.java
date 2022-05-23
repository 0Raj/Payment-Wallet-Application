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

	@Override
	public String electricityBillPayment(BillPayment billPayment) {
		List<CurrentUserSession> list = sessionDAO.findAll();
		Integer id = list.get(0).getCustomerId();
		
		Wallet wallet = customerDAO.getById(id).getWallet();
		
		if(wallet.getWalletBalance() < billPayment.getAmount()) {
			throw new InsufficientAmountException("Insufficient amount in wallet");
		}
		
		BillPayment billPayment2 = new BillPayment(BillType.ELECTRICITY_BILL, billPayment.getAmount(),LocalDateTime.now(), wallet.getWalletId());
		wallet.setWalletBalance(wallet.getWalletBalance() - billPayment.getAmount());
		Transaction myTransaction = new Transaction(TransactionType.WALLET_TO_ELECTRICITY_BILL, LocalDateTime.now(), billPayment.getAmount(), "Electricity bill is paid from wallet", wallet.getWalletId());
		
		transactionDAO.save(myTransaction);
		
		walletDAO.save(wallet);
		billPaymentDAO.save(billPayment2);

		return "Electricity bill paid successfully";
	}

	@Override
	public String mobileRechargeBillPayment(BillPayment billPayment) {
		List<CurrentUserSession> list = sessionDAO.findAll();
		Integer id = list.get(0).getCustomerId();
		
		Wallet wallet = customerDAO.getById(id).getWallet();
		
		if(wallet.getWalletBalance() < billPayment.getAmount()) {
			throw new InsufficientAmountException("Insufficient amount in wallet");
		}
		
		BillPayment billPayment2 = new BillPayment(BillType.MOBILE_RECHARGE, billPayment.getAmount(),LocalDateTime.now(), wallet.getWalletId());
		wallet.setWalletBalance(wallet.getWalletBalance() - billPayment.getAmount());
		Transaction myTransaction = new Transaction(TransactionType.WALLET_TO_MOBILE_RECHARGE, LocalDateTime.now(), billPayment.getAmount(), "Mobile is recharged from wallet", wallet.getWalletId());
		
		transactionDAO.save(myTransaction);
		
		walletDAO.save(wallet);
		billPaymentDAO.save(billPayment2);

		return "Mobile recharge is done successfully";
	}

	@Override
	public List<BillPayment> viewBillPayment() {
		List<CurrentUserSession> list = sessionDAO.findAll();
		Integer id = list.get(0).getCustomerId();
		
		Wallet wallet = customerDAO.getById(id).getWallet();
		
		List<BillPayment> list2 = billPaymentDAO.findAllBillPaymentsByWalletId(wallet.getWalletId());
		
		return list2;
	}
	
}
