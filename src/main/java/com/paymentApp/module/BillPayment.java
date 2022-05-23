package com.paymentApp.module;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillPayment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer billId;
	
	private BillType billType;
	
	private double amount;
	
	private LocalDateTime paymentDateTime;
	
	private Integer walletId;

	public BillPayment(BillType billType, double amount, LocalDateTime paymentDateTime, Integer walletId) {
		super();
		this.billType = billType;
		this.amount = amount;
		this.paymentDateTime = paymentDateTime;
		this.walletId = walletId;
	}
	
}
