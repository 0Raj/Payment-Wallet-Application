package com.paymentApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentApp.exceptions.BeneficiaryAlreadyExists;
import com.paymentApp.exceptions.NotFoundException;
import com.paymentApp.exceptions.UserAlreadyExistWithMobileNumber;
import com.paymentApp.exceptions.UserNotFoundException;
import com.paymentApp.module.Beneficiary;
import com.paymentApp.module.CurrentUserSession;
import com.paymentApp.module.Customer;
import com.paymentApp.module.Wallet;
import com.paymentApp.repository.BeneficiaryDAO;
import com.paymentApp.repository.CustomerDAO;
import com.paymentApp.repository.SessionDAO;
import com.paymentApp.repository.WalletDAO;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService{

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private WalletDAO walletDAO;
	
	@Autowired
	private BeneficiaryDAO beneficiaryDAO;
	
	
	@Override
	public String addBeneficiary(Beneficiary beneficiary) throws NotFoundException , BeneficiaryAlreadyExists{
		
		List<CurrentUserSession> list = sessionDAO.findAll();
		CurrentUserSession currentUserSession =  list.get(0);
		Integer id = currentUserSession.getCustomerId();
		
		Customer customer = customerDAO.getById(id);
		Wallet wallet = customer.getWallet();
		
		Optional<Customer> opt = customerDAO.findByMobileNo(beneficiary.getMobileNumber());
		
		
		if(!opt.isPresent()||!opt.get().getName().equals(beneficiary.getName())) {
			throw new NotFoundException("Customer does not exists...");
		}
		
		List<Beneficiary> beneficiaries = beneficiaryDAO.findAll();
		
		for(Beneficiary beneficiary2 : beneficiaries) {
			if(beneficiary2.getMobileNumber().equals(beneficiary.getMobileNumber())){
				throw new BeneficiaryAlreadyExists("Beneficiary already exists");
			}
		}
		
		wallet.getBeneficiary().add(beneficiary);
		
		beneficiaryDAO.save(beneficiary);
		
		
		return beneficiary.toString();
	}

	@Override
	public String deleteBeneficiary(Beneficiary beneficiary) throws NotFoundException {
		
		Customer customer = customerDAO.getById(sessionDAO.findAll().get(0).getCustomerId());
		
		Wallet wallet = customer.getWallet();
		
		List<Beneficiary> beneficiaryList = wallet.getBeneficiary();
		
		for(Beneficiary beneficiary2 : beneficiaryList) {
			if(beneficiary2.getMobileNumber().equals(beneficiary.getMobileNumber())){
				beneficiaryList.remove(beneficiary2);
				walletDAO.save(wallet);
				
				beneficiaryDAO.delete(beneficiary2);
				
				return "Deleted sucessfully...";
			}
		}

		throw new NotFoundException("Beneficiacry does not exists...");
		
		
	}

	@Override
	public Beneficiary viewBeneficiary(String mobileNo) throws NotFoundException {
		
		Optional<Beneficiary> opt = beneficiaryDAO.findByMobileNumber(mobileNo);
		
		if(!opt.isPresent()) {
			throw new NotFoundException("Beneficiary does not exists...");
		}
		
		return opt.get();
	}

	@Override
	public List<Beneficiary> getAllBeneficiary() throws NotFoundException{
		
		List<Beneficiary> beneficiaries = beneficiaryDAO.findAll();
		
		if(beneficiaries.size()==0) {
			throw new NotFoundException("Beneficiaries not found");
		}
		
		return beneficiaries;
	}
	
	
}
