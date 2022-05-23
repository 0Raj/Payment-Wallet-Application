package com.paymentApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymentApp.module.Beneficiary;
import com.paymentApp.service.BeneficiaryServiceImpl;

@RestController
public class BeneficiaryController {
	
	@Autowired
	private BeneficiaryServiceImpl beneficiaryServiceImpl;
	
//	Add Beneficiary to Wallet
	@PostMapping(value = "/beneficiary")
	public String addBeneficiaryInMyWallet(@RequestBody Beneficiary beneficiary) {
		return beneficiaryServiceImpl.addBeneficiary(beneficiary);
	}
	
//	Delete Beneficiary to from wallet
	@DeleteMapping(value = "/beneficiary")
	public String deleteBeneficiaryInMyWallet(@RequestBody Beneficiary beneficiary) {
		return beneficiaryServiceImpl.deleteBeneficiary(beneficiary);
	}	
	
//	Add Beneficiary to Wallet
	@GetMapping(value = "/beneficiary")
	public Beneficiary viewBeneficiaryInMyWallet(@PathVariable String mbilNo) {
		return beneficiaryServiceImpl.viewBeneficiary(mbilNo);
	}
	
//	Add Beneficiary to Wallet
	@GetMapping(value = "/beneficiaries")
	public List<Beneficiary> getAllBeneficiaryInMyWallet(@RequestBody Beneficiary beneficiary) {
		return beneficiaryServiceImpl.getAllBeneficiary();
	}
	
}
