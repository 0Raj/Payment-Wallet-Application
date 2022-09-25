package com.paymentApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentApp.module.AmountDTO;
import com.paymentApp.module.BillPayment;
import com.paymentApp.module.Transaction;
import com.paymentApp.service.BillPaymentServicesImpl;

@RestController
public class BillPaymentController {
	
	@Autowired
	private BillPaymentServicesImpl billPaymentServicesImpl;

//	To Pay electricity Bill
	@PostMapping("/electricity")
	public String payElectricityBill(@Valid @RequestBody AmountDTO amountDTO ) {
		return billPaymentServicesImpl.electricityBillPayment(amountDTO.getAmount());
	}
	
//	To recharges mobile phone
	@PostMapping("/recharge")
	public String mobileRechargeBillPayment(@Valid @RequestBody AmountDTO amountDTO ) {
		return billPaymentServicesImpl.mobileRechargeBillPayment(amountDTO.getAmount());
	}
	
//	To get all bill payments
	@GetMapping("/bills")
	public List<BillPayment> getAllBillPayments() {
		return billPaymentServicesImpl.viewBillPayment();
	}
	
//	Get all transaction history
	@GetMapping("/transactions")
	public List<Transaction> getAllTransactions() {
		return billPaymentServicesImpl.getAllTransactions();
	}
}
