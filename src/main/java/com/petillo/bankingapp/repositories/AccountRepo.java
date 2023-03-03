package com.petillo.bankingapp.repositories;

import com.petillo.bankingapp.models.Account;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {

    List<Account> findAllByClientId(String clientId);
}
