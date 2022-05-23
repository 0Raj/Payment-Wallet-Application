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
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer transectionId;
	
	private TransactionType transactionType;
	
	private LocalDateTime transactionDate;
	
	private double amount;
	
	private String description;
	
	private Integer walletId;

	public Transaction(TransactionType transactionType, LocalDateTime transactionDate, double amount,
			String description, Integer walletId) {
		super();
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.description = description;
		this.walletId = walletId;
	}

}
