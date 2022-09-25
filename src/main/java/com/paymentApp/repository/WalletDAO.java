package com.paymentApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentApp.module.Wallet;

public interface WalletDAO extends JpaRepository<Wallet, Integer>{

    @Override
    Optional<Wallet> findById(Integer integer);



}
