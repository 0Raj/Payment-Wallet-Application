package com.paymentApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentApp.module.Wallet;

import java.util.Optional;

public interface WalletDAO extends JpaRepository<Wallet, Integer>{

    @Override
    Optional<Wallet> findById(Integer integer);
}
