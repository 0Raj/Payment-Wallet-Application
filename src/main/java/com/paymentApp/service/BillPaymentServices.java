package com.paymentApp.service;

import java.util.List;

import com.paymentApp.module.BillPayment;

public interface BillPaymentServices {

	public String electricityBillPayment(BillPayment billPayment);
	
	public String mobileRechargeBillPayment(BillPayment billPayment);
	
	public List<BillPayment> viewBillPayment();
}
