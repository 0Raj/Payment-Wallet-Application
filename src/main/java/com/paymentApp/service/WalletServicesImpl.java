package com.paymentApp.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymentApp.exceptions.InsufficientAmountException;
import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.module.Bank;
import com.paymentApp.module.Customer;
import com.paymentApp.module.FundTransferDTO;
import com.paymentApp.module.Transaction;
import com.paymentApp.module.TransactionType;
import com.paymentApp.module.Wallet;
import com.paymentApp.repository.BankDAO;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.repository.TransactionDAO;
import com.paymentApp.repository.WalletDAO;
import com.paymentApp.util.GetCurrentLoginUserDetails;

@Service
public class WalletServicesImpl implements WalletService {

	@Autowired
	private BankDAO bankDAO;

	@Autowired
	private WalletDAO walletDAO;

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private TransactionDAO transactionDAO;

	@Autowired
	private GetCurrentLoginUserDetails getCurrentLoginUserDetails;

	@Override
	public Bank addBank(Bank bank) {

		Optional<Customer> optionalCustomer = customerDAO.findByMobileNo(bank.getMobileNumber());

		if (!optionalCustomer.isPresent()) {
			throw new NotFoundException("Please Enter Mobile number attached with your wallet");
		}

		Customer customer = optionalCustomer.get();

		Optional<Wallet> optionalWallet = walletDAO.findById(customer.getWallet().getWalletId());

		Wallet wallet = optionalWallet.get();

		wallet.setBank(bank);

		bank.setWalletId(wallet.getWalletId());
		walletDAO.save(wallet);

		return bankDAO.save(bank);
	}

	@Override
	public String removeBank() {

		if (getCurrentLoginUserDetails.checkLogin()) {

			Customer customer = getCurrentLoginUserDetails.getCurrentCustomer();
			Wallet wallet = customer.getWallet();

			Optional<Bank> optBank = bankDAO.findByMobileNumber(customer.getMobileNo());

			wallet.setBank(null);

			walletDAO.save(wallet);

			bankDAO.delete(optBank.get());

			return "Bank is deleted sucessfully";
		} else {

			return "Bank is Not deleted sucessfully";
		}

	}

	@Override
	public double showBankBalance() {

		if (getCurrentLoginUserDetails.checkLogin()) {

			Customer customer = getCurrentLoginUserDetails.getCurrentCustomer();

			Optional<Bank> optBank = bankDAO.findByMobileNumber(customer.getMobileNo());

			if (!optBank.isPresent()) {
				throw new NotFoundException("Account is not found");
			}
			return optBank.get().getBankBalance();

		} else {
			throw new UsernameNotFoundException("Please Login First");
		}

	}

	@Override
	public double showWalletBalance() throws NotFoundException {

		if (getCurrentLoginUserDetails.checkLogin()) {

			Customer customer = getCurrentLoginUserDetails.getCurrentCustomer();
			return customer.getWallet().getWalletBalance();

		} else {
			throw new UsernameNotFoundException("Please Login First");
		}

	}

	// transaction module completed
	@Override
	@Transactional
	public String fundTransterFromWalletToWallet(FundTransferDTO fundTransferDTO) {

		if (getCurrentLoginUserDetails.checkLogin()) {

			Customer customer = getCurrentLoginUserDetails.getCurrentCustomer();

			Optional<Wallet> optionalWallet = walletDAO.findById(customer.getWallet().getWalletId());

			Wallet sourceWallet = optionalWallet.get();

			if (sourceWallet.getWalletBalance() < fundTransferDTO.getAmount()) {
				throw new InsufficientAmountException("Insufficient balance in wallet");
			}

			Optional<Customer> optTargetCustomer = customerDAO.findByMobileNo(fundTransferDTO.getTargetMobileNo());

			if (!optTargetCustomer.isPresent()) {
				throw new NotFoundException("Target user mobile number not valid");
			}

			Customer targetCustomer = optTargetCustomer.get();

			Optional<Wallet> optTargetWallet = walletDAO.findById(targetCustomer.getWallet().getWalletId());
			Wallet targetWallet = optTargetWallet.get();

			sourceWallet.setWalletBalance(sourceWallet.getWalletBalance() - fundTransferDTO.getAmount());
			targetWallet.setWalletBalance(targetWallet.getWalletBalance() + fundTransferDTO.getAmount());

			Transaction sourceTransaction = new Transaction(TransactionType.WALLET_TO_WALLET_FUND_TRANSFER,
					LocalDateTime.now(), fundTransferDTO.getAmount(), fundTransferDTO.getAmount()
							+ " Rupees is sent to " + targetCustomer.getMobileNo() + " sucessfully...");

			Transaction targetTransaction = new Transaction(TransactionType.WALLET_TO_WALLET_FUND_TRANSFER,
					LocalDateTime.now(), fundTransferDTO.getAmount(), fundTransferDTO.getAmount()
							+ " Rupees is received from " + customer.getMobileNo() + " sucessfully...");

			sourceWallet.getTransactions().add(sourceTransaction);
			targetWallet.getTransactions().add(targetTransaction);

			transactionDAO.save(sourceTransaction);
			transactionDAO.save(targetTransaction);
			walletDAO.save(sourceWallet);
			walletDAO.save(targetWallet);

			return fundTransferDTO.getAmount() + " Rupees is sent to " + targetCustomer.getMobileNo()
					+ " sucessfully...";

		} else {
			throw new NotFoundException("Please Login First");
		}

	}

	@Override
	@Transactional
	public String depositAmount(Double amount) {

		if (getCurrentLoginUserDetails.checkLogin()) {

			Customer customer = getCurrentLoginUserDetails.getCurrentCustomer();

			Optional<Wallet> optionalWallet = walletDAO.findById(customer.getWallet().getWalletId());

			Wallet wallet = optionalWallet.get();

			if (wallet.getWalletBalance() < amount) {
				throw new InsufficientAmountException("Insufficient amount in wallet");
			}

			wallet.setWalletBalance(wallet.getWalletBalance() - amount);

			Bank bank = wallet.getBank();

			bank.setBankBalance(bank.getBankBalance() + amount);

			bankDAO.save(bank);

			String description = amount + " is deposited to your bank account";

			Transaction myTransaction = new Transaction(TransactionType.WALLET_TO_ACCOUNT, LocalDateTime.now(), amount,
					description);
			wallet.getTransactions().add(myTransaction);

			transactionDAO.save(myTransaction);
			return description;

		} else {
			throw new NotFoundException("Please Login First");
		}

	}

	@Override
	@Transactional
	public String addMoney(Double amount) {

		if (getCurrentLoginUserDetails.checkLogin()) {

			Customer customer = getCurrentLoginUserDetails.getCurrentCustomer();

			Optional<Wallet> optionalWallet = walletDAO.findById(customer.getWallet().getWalletId());

			Wallet wallet = optionalWallet.get();

			Bank bank = wallet.getBank();

			if (bank == null) {
				throw new NotFoundException("No bank is linked with this account");
			}

			if (bank.getBankBalance() < amount) {
				throw new InsufficientAmountException("Insufficient amount in your bank");
			}

			bank.setBankBalance(bank.getBankBalance() - amount);

			wallet.setWalletBalance(wallet.getWalletBalance() + amount);

			bankDAO.save(bank);
			walletDAO.save(wallet);

			Transaction myTransaction = new Transaction(TransactionType.WALLET_TO_ACCOUNT, LocalDateTime.now(), amount,
					amount + " is credited in your wallet");
			wallet.getTransactions().add(myTransaction);

			transactionDAO.save(myTransaction);

			return amount + " is credited to your wallet";

		} else {
			throw new NotFoundException("Please Login First");
		}

	}
}
