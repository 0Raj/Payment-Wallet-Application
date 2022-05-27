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
<<<<<<< Updated upstream
	@PostMapping(value = "/beneficiary/{key}")
	public String addBeneficiaryInMyWallet(@RequestBody Beneficiary beneficiary, @PathVariable("key") String key) {
=======
	@PostMapping(value = "/beneficiary")
	public String addBeneficiaryInMyWallet(@RequestBody Beneficiary beneficiary, @RequestParam(required = false) String key) {
>>>>>>> Stashed changes
		return beneficiaryServiceImpl.addBeneficiary(beneficiary, key);
	}
	
//	Delete Beneficiary to from wallet by passing this object
//	{
//	"name":"Jay",
//	"mobileNumber":"9981595557"
//}
<<<<<<< Updated upstream
	@DeleteMapping(value = "/beneficiary/{key}")
	public String deleteBeneficiaryInMyWallet(@RequestBody Beneficiary beneficiary, @PathVariable("key") String key) {
=======
	@DeleteMapping(value = "/beneficiary")
	public String deleteBeneficiaryInMyWallet(@RequestBody Beneficiary beneficiary, @RequestParam(required = false) String key) {
>>>>>>> Stashed changes
		return beneficiaryServiceImpl.deleteBeneficiary(beneficiary, key);
	}	
	
//	Get Beneficiary to Wallet
//	By passing mobile number
<<<<<<< Updated upstream
	@GetMapping(value = "/beneficiary/{key}")
	public Beneficiary viewBeneficiaryInMyWallet(@PathVariable String mbilNo, @PathVariable("key") String key) {
=======
	@GetMapping(value = "/beneficiary")
	public Beneficiary viewBeneficiaryInMyWallet(@PathVariable String mbilNo,@RequestParam(required = false) String key) {
>>>>>>> Stashed changes
		return beneficiaryServiceImpl.viewBeneficiary(mbilNo, key);
	}
	
//	Get All Beneficiaries to Wallet
<<<<<<< Updated upstream
	@GetMapping(value = "/beneficiaries/{key}")
	public List<Beneficiary> getAllBeneficiaryInMyWallet(@PathVariable("key") String key) {
=======
	@GetMapping(value = "/beneficiaries")
	public List<Beneficiary> getAllBeneficiaryInMyWallet( @RequestParam(required = false) String key) {
>>>>>>> Stashed changes
		return beneficiaryServiceImpl.getAllBeneficiary(key);
	}
	
}
