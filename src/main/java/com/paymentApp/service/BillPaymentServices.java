package com.paymentApp.service;

import java.util.List;

import com.paymentApp.module.BillPayment;

public interface BillPaymentServices {

	public String electricityBillPayment();
	
	public String mobileRechargeBillPayment();
	
	public List<BillPayment> viewBillPayment();
}
