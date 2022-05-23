package com.paymentApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentApp.module.Customer;

public interface CustomerDAO extends JpaRepository<Customer, Integer>{
	
	public Optional<Customer> findByMobileNo(String mobileNo);
	
}
