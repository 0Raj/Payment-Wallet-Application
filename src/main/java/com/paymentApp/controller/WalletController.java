package com.paymentApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.paymentApp.module.AmountDTO;
import com.paymentApp.module.Bank;
import com.paymentApp.module.FundTransferDTO;
import com.paymentApp.service.WalletServicesImpl;

@RestController
public class WalletController {
	
	@Autowired
	private WalletServicesImpl walletServicesImpl;
	
//	Add a Bank to wallet
	@PostMapping("/bank")
	public Bank addBankToWallet(@Valid @RequestBody Bank bank, @RequestParam(required = false) String key) {
		return walletServicesImpl.addBank(bank, key);
	}
	
//	Delete a Bank from wallet
	@DeleteMapping("/bank")
	public String deleteBankStringAccount(@RequestParam(required = false) String key) {
		return walletServicesImpl.removeBank(key);
	}
	
//	Get the Bank balance
	@GetMapping("/bankbalance")
	public double showBankBalance( @RequestParam(required = false) String key) {
		return walletServicesImpl.showBankBalance(key);
	}
	
//	Get the Wallet balance
	@GetMapping("/walletbalance")
	public double showWalletBalance( @RequestParam(required = false) String key) {
		return walletServicesImpl.showWalletBalance(key);
	}
	
//	Fund transfer from source mobile to target mobile
	@PostMapping("/transfer")
	public String fundTransferToWallet(@RequestBody FundTransferDTO fundTransferDTO, @RequestParam(required = false) String key) {
		return walletServicesImpl.fundTransterFromWalletToWallet(fundTransferDTO, key);
	}
	
//	Add money from bank to wallet
	@PostMapping(value = "/addmoney")
	public String addMoneyToWalletFromBank(@RequestBody AmountDTO addMoneyToWalletOrBankDTO, @RequestParam(required = false) String key) {
		return walletServicesImpl.addMoney(addMoneyToWalletOrBankDTO.getAmount(), key);
	}
	
//	Deposit money to bank from wallet
	@PostMapping(value = "/deposit")
	public String depositMoneyToBankFromWallet(@RequestBody AmountDTO addMoneyToWalletOrBankDTO, @RequestParam(required = false) String key) {
		return walletServicesImpl.depositAmount(addMoneyToWalletOrBankDTO.getAmount(), key);
	}
	

}
