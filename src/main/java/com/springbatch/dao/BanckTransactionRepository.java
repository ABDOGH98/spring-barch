package com.springbatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BanckTransactionRepository extends JpaRepository<BankTransaction,Long> {
}
