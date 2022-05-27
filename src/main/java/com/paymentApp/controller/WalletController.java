package com.paymentApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
<<<<<<< Updated upstream
	@PostMapping("/bank/{key}")
	public Bank addBankToWallet(@Valid @RequestBody Bank bank, @PathVariable("key") String key) {
=======
	@PostMapping("/bank")
	public Bank addBankToWallet(@Valid @RequestBody Bank bank, @RequestParam(required = false) String key) {
>>>>>>> Stashed changes
		return walletServicesImpl.addBank(bank, key);
	}
	
//	Delete a Bank from wallet
<<<<<<< Updated upstream
	@DeleteMapping("/bank/{key}")
	public String deleteBankStringAccount(@PathVariable("key") String key) {
=======
	@DeleteMapping("/bank")
	public String deleteBankStringAccount(@RequestParam(required = false) String key) {
>>>>>>> Stashed changes
		return walletServicesImpl.removeBank(key);
	}
	
//	Get the Bank balance
<<<<<<< Updated upstream
	@GetMapping("/bankbalance/{key}")
	public double showBankBalance( @PathVariable("key") String key) {
=======
	@GetMapping("/bankbalance")
	public double showBankBalance( @RequestParam(required = false) String key) {
>>>>>>> Stashed changes
		return walletServicesImpl.showBankBalance(key);
	}
	
//	Get the Wallet balance
<<<<<<< Updated upstream
	@GetMapping("/walletbalance/{key}")
	public double showWalletBalance( @PathVariable("key") String key) {
=======
	@GetMapping("/walletbalance")
	public double showWalletBalance( @RequestParam(required = false) String key) {
>>>>>>> Stashed changes
		return walletServicesImpl.showWalletBalance(key);
	}
	
//	Fund transfer from source mobile to target mobile
<<<<<<< Updated upstream
	@PostMapping("/transfer/{key}")
	public String fundTransferToWallet(@RequestBody FundTransferDTO fundTransferDTO, @PathVariable("key") String key) {
=======
	@PostMapping("/transfer")
	public String fundTransferToWallet(@RequestBody FundTransferDTO fundTransferDTO, @RequestParam(required = false) String key) {
>>>>>>> Stashed changes
		return walletServicesImpl.fundTransterFromWalletToWallet(fundTransferDTO, key);
	}
	
//	Add money from bank to wallet
<<<<<<< Updated upstream
	@PostMapping(value = "/add/{key}")
	public String addMoneyToWalletFromBank(@RequestBody AddMoneyToWalletOrBankDTO addMoneyToWalletOrBankDTO, @PathVariable("key") String key) {
=======
	@PostMapping(value = "/addmoney")
	public String addMoneyToWalletFromBank(@RequestBody AmountDTO addMoneyToWalletOrBankDTO, @RequestParam(required = false) String key) {
>>>>>>> Stashed changes
		return walletServicesImpl.addMoney(addMoneyToWalletOrBankDTO.getAmount(), key);
	}
	
//	Deposit money to bank from wallet
<<<<<<< Updated upstream
	@PostMapping(value = "/deposit/{key}")
	public String depositMoneyToBankFromWallet(@RequestBody AddMoneyToWalletOrBankDTO addMoneyToWalletOrBankDTO, @PathVariable("key") String key) {
=======
	@PostMapping(value = "/deposit")
	public String depositMoneyToBankFromWallet(@RequestBody AmountDTO addMoneyToWalletOrBankDTO, @RequestParam(required = false) String key) {
>>>>>>> Stashed changes
		return walletServicesImpl.depositAmount(addMoneyToWalletOrBankDTO.getAmount(), key);
	}
	

}
