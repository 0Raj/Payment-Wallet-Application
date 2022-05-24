package com.paymentApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymentApp.module.BillPayment;
import com.paymentApp.service.BillPaymentServicesImpl;

@RestController
public class BillPaymentController {
	
	@Autowired
	private BillPaymentServicesImpl billPaymentServicesImpl;

//	To Pay electricity Bill
	@PostMapping("/electricity")
	public String payElectricityBill() {
		return billPaymentServicesImpl.electricityBillPayment();
	}
	
//	To recharges mobile phone
	@PostMapping("/recharge")
	public String mobileRechargeBillPayment() {
		return billPaymentServicesImpl.mobileRechargeBillPayment();
	}
	
//	To get all bill payments
	@GetMapping("/bills")
	public List<BillPayment> getAllBillPayments() {
		return billPaymentServicesImpl.viewBillPayment();
	}
	
}
