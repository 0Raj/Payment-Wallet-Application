package com.paymentApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentApp.module.Beneficiary;

public interface BeneficiaryDAO extends JpaRepository<Beneficiary, Integer>{
	
	public Optional<Beneficiary>  findByMobileNumber(String mobileNumber);
	
//	public List<Beneficiary> findAllBeneficiariesByWalletId(Integer id);
}
