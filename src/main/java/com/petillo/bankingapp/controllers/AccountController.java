package com.petillo.bankingapp.controllers;

import com.petillo.bankingapp.models.Account;
import com.petillo.bankingapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/{accountNum}")
    public Account getAccountById(@PathVariable int accountNum){
        return accountService.getAccountByAccountNum(accountNum);
    }

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/clients/{id}")
    public List<Account> getAllAccountsByClientId(@PathVariable String id){
        return accountService.getAllAccountsByClientId(id);
    }

    @PostMapping
    public Account addAccount(@RequestBody Account newAccount){
        return accountService.addAccount(newAccount);
    }

    @PutMapping("/{accountNum}")
    public Account updateAccount(@PathVariable int accountNum, @RequestBody Account updatedAccount){
        return accountService.updateAccount(updatedAccount);
    }

    @DeleteMapping("/{accountNum}")
    public void deleteAccountById(@PathVariable int accountNum){
        accountService.deleteAccountByAcctNum(accountNum);
    }
}
