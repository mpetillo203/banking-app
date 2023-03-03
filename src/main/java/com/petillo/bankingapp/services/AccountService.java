package com.petillo.bankingapp.services;

import com.petillo.bankingapp.models.Account;
import com.petillo.bankingapp.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepo accountRepo;

    private final String itemNotFound = "Account not found.";

    @Autowired
    public AccountService(AccountRepo accountRepo){
        this.accountRepo = accountRepo;
    }

    public Account getAccountByAccountNum(int accountNum){
        return accountRepo.findById(accountNum).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, itemNotFound));
    }

    public List<Account> getAllAccounts(){
        return accountRepo.findAll();
    }

    public List<Account> getAllAccountsByClientId(String clientId){
        return accountRepo.findAllByClientId(clientId);
    }

    public Account addAccount(Account newAcct){
        return accountRepo.save(newAcct);
    }

    public Account updateAccount(Account updatedAcct){
        return accountRepo.save(updatedAcct);
    }

    //Add a check that the acct cannot be deleted if the bal is >0
    public void deleteAccountByAcctNum(int accountNum){
        accountRepo.deleteById(accountNum);
    }
}
