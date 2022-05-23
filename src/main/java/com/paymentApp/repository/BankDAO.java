package com.paymentApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentApp.module.Bank;

public interface BankDAO extends JpaRepository<Bank, Integer>{
	
}
