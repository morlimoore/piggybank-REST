package com.morlimoore.piggybankapi.repositories;

import com.morlimoore.piggybankapi.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

//    @Query(value="SELECT * FROM transactions WHERE user_id = ?1", nativeQuery = true)
    List<Transaction> findAllTransactionsByUser(Long user_id);
}