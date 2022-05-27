package com.paymentApp.service;

import java.util.List;
import com.paymentApp.module.BillPayment;

public interface BillPaymentServices {

	public String electricityBillPayment(Double amount , String key);
	
	public String mobileRechargeBillPayment(Double amount , String key);
	
	public List<BillPayment> viewBillPayment(String key);
}
