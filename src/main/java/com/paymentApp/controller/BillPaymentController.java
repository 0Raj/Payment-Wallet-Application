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
	public String payElectricityBill(@RequestBody BillPayment billPayment) {
		return billPaymentServicesImpl.electricityBillPayment(billPayment);
	}
	
//	To recharge mobile phones
	@PostMapping("/recharge")
	public String mobileRechargeBillPayment(@RequestBody BillPayment billPayment) {
		return billPaymentServicesImpl.mobileRechargeBillPayment(billPayment);
	}
	
//	To get all bill payments
	@GetMapping("/bills")
	public List<BillPayment> getAllBillPayments() {
		return billPaymentServicesImpl.viewBillPayment();
	}
	
}
