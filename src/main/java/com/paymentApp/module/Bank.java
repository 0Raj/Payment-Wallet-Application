package com.paymentApp.module;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Bank {
	
	@Id
	private Integer accountNumber;
	
	@NotNull
	private String ifscCode;
	
	@NotNull
	private String bankName;
	
	@NotNull
	private double bankBalance;
	
	private Integer walletId;
	
}
