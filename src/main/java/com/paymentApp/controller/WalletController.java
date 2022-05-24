package com.paymentApp.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.paymentApp.module.AddMoneyToWalletOrBankDTO;
import com.paymentApp.module.Bank;
import com.paymentApp.module.FundTransferDTO;
import com.paymentApp.service.WalletServicesImpl;

@RestController
public class WalletController {
	@Autowired
	private WalletServicesImpl walletServicesImpl;
	
//	Add a Bank to wallet
	@PostMapping("/bank/{key}")
	public Bank addBankToWallet(@Valid @RequestBody Bank bank, @PathVariable("key") String key) {
		return walletServicesImpl.addBank(bank, key);
	}
	
//	Delete a Bank from wallet
	@DeleteMapping("/bank/{key}")
	public String deleteBankStringAccount(@PathVariable("key") String key) {
		return walletServicesImpl.removeBank(key);
	}
	
//	Get the Bank balance
	@GetMapping("/bankbalance/{key}")
	public double showBankBalance( @PathVariable("key") String key) {
		return walletServicesImpl.showBankBalance(key);
	}
	
//	Get the Wallet balance
	@GetMapping("/walletbalance/{key}")
	public double showWalletBalance( @PathVariable("key") String key) {
		return walletServicesImpl.showWalletBalance(key);
	}
	
//	Fund transfer from source mobile to target mobile
	@PostMapping("/transfer/{key}")
	public String fundTransferToWallet(@RequestBody FundTransferDTO fundTransferDTO, @PathVariable("key") String key) {
		return walletServicesImpl.fundTransterFromWalletToWallet(fundTransferDTO, key);
	}
	
//	Add money from bank to wallet
	@PostMapping(value = "/add/{key}")
	public String addMoneyToWalletFromBank(@RequestBody AddMoneyToWalletOrBankDTO addMoneyToWalletOrBankDTO, @PathVariable("key") String key) {
		return walletServicesImpl.addMoney(addMoneyToWalletOrBankDTO.getAmount(), key);
	}
	
//	Deposit money to bank from wallet
	@PostMapping(value = "/deposit/{key}")
	public String depositMoneyToBankFromWallet(@RequestBody AddMoneyToWalletOrBankDTO addMoneyToWalletOrBankDTO, @PathVariable("key") String key) {
		return walletServicesImpl.depositAmount(addMoneyToWalletOrBankDTO.getAmount(), key);
	}
	

}
