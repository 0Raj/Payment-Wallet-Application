package com.paymentApp.service;

import java.util.List;

import com.paymentApp.exceptions.BeneficiaryAlreadyExists;
import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.module.Beneficiary;

public interface BeneficiaryService {
	
	public String addBeneficiary(Beneficiary beneficiary, String key) throws NotFoundException , BeneficiaryAlreadyExists  ;
	
	public String deleteBeneficiary(Beneficiary beneficiary, String key) throws NotFoundException;
	
	public Beneficiary viewBeneficiary(String mobileNo, String key) throws NotFoundException;
	
	public List<Beneficiary> getAllBeneficiary(String key) throws NotFoundException;
}
