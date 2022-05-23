package com.paymentApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentApp.module.Transaction;

public interface TransactionDAO extends JpaRepository<Transaction, Integer>{

}
