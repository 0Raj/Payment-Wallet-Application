package com.paymentApp.service;

import java.util.List;
import com.paymentApp.module.BillPayment;
import com.paymentApp.module.Transaction;

public interface BillPaymentServices {

	public String electricityBillPayment(Double amount);
	
	public String mobileRechargeBillPayment(Double amount);
	
	public List<BillPayment> viewBillPayment();
	
	public List<Transaction> getAllTransactions();
}
