package com.paymentApp.module;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Beneficiary {
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int beneficiaryId;

	private String name;
	
	private String mobileNumber;

	public Beneficiary(String name, String mobileNumber) {
		super();
		this.name = name;
		this.mobileNumber = mobileNumber;
	}

}
