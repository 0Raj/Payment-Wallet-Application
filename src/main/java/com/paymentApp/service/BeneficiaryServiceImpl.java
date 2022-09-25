package com.paymentApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.BeneficiaryAlreadyExists;
import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.module.Beneficiary;
import com.paymentApp.module.Customer;
import com.paymentApp.module.Wallet;
import com.paymentApp.repository.BeneficiaryDAO;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.repository.WalletDAO;
import com.paymentApp.util.GetCurrentLoginUserDetails;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private WalletDAO walletDAO;

	@Autowired
	private BeneficiaryDAO beneficiaryDAO;

	@Autowired
	private GetCurrentLoginUserDetails getCurrentLoginUserDetails;

	@Override
	public String addBeneficiary(Beneficiary beneficiary) {

		Wallet wallet = getCurrentLoginUserDetails.getCurrentUserWallet();

		Optional<Customer> opt = customerDAO.findByMobileNo(beneficiary.getMobileNumber());

		if (!opt.isPresent()) {
			throw new NotFoundException("Customer does not exists...");
		}

		List<Beneficiary> beneficiaries = beneficiaryDAO.findAll();

		for (Beneficiary beneficiary2 : beneficiaries) {
			if (beneficiary2.getMobileNumber().equals(beneficiary.getMobileNumber())) {
				throw new BeneficiaryAlreadyExists("Beneficiary already exists");
			}
		}

		wallet.getBeneficiary().add(beneficiary);

		beneficiaryDAO.save(beneficiary);

		return beneficiary.toString();
	}

	@Override
	public String deleteBeneficiary(Beneficiary beneficiary) {

		Wallet wallet = getCurrentLoginUserDetails.getCurrentUserWallet();

		List<Beneficiary> beneficiaryList = wallet.getBeneficiary();

		for (Beneficiary beneficiary2 : beneficiaryList) {
			if (beneficiary2.getMobileNumber().equals(beneficiary.getMobileNumber())) {
				beneficiaryList.remove(beneficiary2);
				walletDAO.save(wallet);

				beneficiaryDAO.delete(beneficiary2);

				return "Deleted sucessfully...";
			}
		}

		throw new NotFoundException("Beneficiacry does not exists...");

	}

	@Override
	public Beneficiary viewBeneficiary(String mobileNo) {

		Optional<Beneficiary> opt = beneficiaryDAO.findByMobileNumber(mobileNo);

		if (!opt.isPresent()) {
			throw new NotFoundException("Beneficiary does not exists...");
		}

		return opt.get();
	}

	@Override
	public List<Beneficiary> getAllBeneficiary() {

		List<Beneficiary> beneficiaries = beneficiaryDAO.findAll();

		if (beneficiaries.size() == 0) {
			throw new NotFoundException("Beneficiaries not found");
		}

		return beneficiaries;
	}

}
