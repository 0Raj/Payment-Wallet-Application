package com.paymentApp.service;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.InsufficientAmountException;
import com.paymentApp.module.BillPayment;
import com.paymentApp.module.BillType;
import com.paymentApp.module.Transaction;
import com.paymentApp.module.TransactionType;
import com.paymentApp.module.Wallet;
import com.paymentApp.repository.BillPaymentDAO;
import com.paymentApp.repository.TransactionDAO;
import com.paymentApp.repository.WalletDAO;
import com.paymentApp.util.GetCurrentLoginUserSessionDetailsImpl;

@Service
public class BillPaymentServicesImpl implements BillPaymentServices{
	
	@Autowired
	private BillPaymentDAO billPaymentDAO;
	
	@Autowired
	private WalletDAO walletDAO;
	
	@Autowired
	private TransactionDAO transactionDAO;

	@Autowired
	private GetCurrentLoginUserSessionDetailsImpl getCurrentLoginUser;
		
	
	@Override
	public String electricityBillPayment(Double amount, String key) {
		
		Wallet wallet = getCurrentLoginUser.getCurrentUserWallet(key);
		
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
	public String mobileRechargeBillPayment(Double amount , String key) {

		Wallet wallet = getCurrentLoginUser.getCurrentUserWallet(key);
		
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
	public List<BillPayment> viewBillPayment(String key) {

		Wallet wallet = getCurrentLoginUser.getCurrentUserWallet(key);
		
		List<BillPayment> list2 = billPaymentDAO.findAllBillPaymentsByWalletId(wallet.getWalletId());
		
		return list2;
	}
	
}
