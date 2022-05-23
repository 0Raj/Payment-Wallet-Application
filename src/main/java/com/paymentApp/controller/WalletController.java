package com.paymentApp.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	@PostMapping("/bank")
	public Bank addBankToWallet(@Valid @RequestBody Bank bank) {
		return walletServicesImpl.addBank(bank);
	}
	
//	Delete a Bank from wallet
	@DeleteMapping("/bank")
	public String deleteBankStringAccount() {
		return walletServicesImpl.removeBank();
	}
	
//	Get the Bank balance
	@GetMapping("/bankbalance")
	public double showBankBalance() {
		return walletServicesImpl.showBankBalance();
	}
	
//	Get the Wallet balance
	@GetMapping("/walletbalance")
	public double showWalletBalance() {
		return walletServicesImpl.showWalletBalance();
	}
	
//	Fund transfer from source mobile to target mobile
	@PostMapping("/transfer")
	public String fundTransferToWallet(@RequestBody FundTransferDTO fundTransferDTO) {
		return walletServicesImpl.fundTransterFromWalletToWallet(fundTransferDTO);
	}
	
//	Add money from bank to wallet
	@PostMapping(value = "/add")
	public String addMoneyToWalletFromBank(@RequestBody AddMoneyToWalletOrBankDTO addMoneyToWalletOrBankDTO) {
		return walletServicesImpl.addMoney(addMoneyToWalletOrBankDTO.getAmount());
	}
	
//	Deposit money to bank from wallet
	@PostMapping(value = "/deposit")
	public String depositMoneyToBankFromWallet(@RequestBody AddMoneyToWalletOrBankDTO addMoneyToWalletOrBankDTO) {
		return walletServicesImpl.depositAmount(addMoneyToWalletOrBankDTO.getAmount());
	}
	

}
