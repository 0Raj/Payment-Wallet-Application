package com.paymentApp.repository;

import javax.persistence.Id;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymentApp.module.CurrentUserSession;

@Repository
public interface SessionDAO extends JpaRepository<CurrentUserSession, Id>{

}
