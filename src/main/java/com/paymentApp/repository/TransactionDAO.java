package com.paymentApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentApp.module.Transaction;

public interface TransactionDAO extends JpaRepository<Transaction, Integer>{

	public List<Transaction> findAllTransactionsByWalletId(Integer id);
	
}
