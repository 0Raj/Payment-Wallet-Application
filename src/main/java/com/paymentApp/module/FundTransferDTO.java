package com.paymentApp.module;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FundTransferDTO {
	
	private String targetMobileNo;
	
	private double amount;
}

