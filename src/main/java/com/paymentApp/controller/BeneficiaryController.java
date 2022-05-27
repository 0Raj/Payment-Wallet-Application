package com.paymentApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentApp.module.Beneficiary;
import com.paymentApp.service.BeneficiaryServiceImpl;

@RestController
public class BeneficiaryController {
	
	@Autowired
	private BeneficiaryServiceImpl beneficiaryServiceImpl;
	
//	Add Beneficiary to Wallet by passing this object
//	{
//		"name":"Jay",
//		"mobileNumber":"9981595557"
//	}

	@PostMapping(value = "/beneficiary")
	public String addBeneficiaryInMyWallet(@RequestBody Beneficiary beneficiary, @RequestParam(required = false) String key) {
		return beneficiaryServiceImpl.addBeneficiary(beneficiary, key);
	}
	
//	Delete Beneficiary to from wallet by passing this object
//	{
//	"name":"Jay",
//	"mobileNumber":"9981595557"
//}

	@DeleteMapping(value = "/beneficiary")
	public String deleteBeneficiaryInMyWallet(@RequestBody Beneficiary beneficiary, @RequestParam(required = false) String key) {
		return beneficiaryServiceImpl.deleteBeneficiary(beneficiary, key);
	}	
	
//	Get Beneficiary to Wallet
//	By passing mobile number

	@GetMapping(value = "/beneficiary")
	public Beneficiary viewBeneficiaryInMyWallet(@PathVariable String mbilNo,@RequestParam(required = false) String key) {
		return beneficiaryServiceImpl.viewBeneficiary(mbilNo, key);
	}
	
//	Get All Beneficiaries to Wallet
	@GetMapping(value = "/beneficiaries")
	public List<Beneficiary> getAllBeneficiaryInMyWallet( @RequestParam(required = false) String key) {
		return beneficiaryServiceImpl.getAllBeneficiary(key);
	}
	
}
