package com.paymentApp.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentApp.module.Bank;

import java.util.Optional;

public interface BankDAO extends JpaRepository<Bank, Integer>{

    @Override
    Optional<Bank> findById(Integer integer);

    Optional<Bank> findByMobileNumber(String mobileNumber);
}
