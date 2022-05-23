package com.paymentApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentApp.module.BillPayment;

public interface BillPaymentDAO extends JpaRepository<BillPayment, Integer>{

	public List<BillPayment> findAllBillPaymentsByWalletId(Integer id);
}

