package com.paymentApp.module;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Data
public class BillPayment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer billId;
	
	private BillType billType;
	
	private double amount;
	
	private LocalDateTime paymentDateTime;
	
	private Wallet wallet;
}
