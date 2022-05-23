package com.paymentApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentApp.module.Wallet;

public interface WalletDAO extends JpaRepository<Wallet, Integer>{

}
