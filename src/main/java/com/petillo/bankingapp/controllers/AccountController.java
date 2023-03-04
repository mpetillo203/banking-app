package com.petillo.bankingapp.controllers;

import com.petillo.bankingapp.models.Account;
import com.petillo.bankingapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/{accountNum}")
    public Account getAccountByAccountNum(@PathVariable("accountNum") int accountNum) throws ResponseStatusException {
        return accountService.getAccountByAccountNum(accountNum);
    }

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/clients/{id}")
    public List<Account> getAllAccountsByClientId(@PathVariable("id") int id){
        return accountService.getAllAccountsByClientId(id);
    }

    @PostMapping
    public Account addAccount(@RequestBody Account newAccount){
        return accountService.addAccount(newAccount);
    }

    @PutMapping("/{accountNum}")
    public Account updateAccount(@PathVariable("accountNum") int accountNum, @RequestBody Account updatedAccount) throws ResponseStatusException {
        return accountService.updateAccount(accountNum, updatedAccount);
    }

    @DeleteMapping("/{accountNum}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccountById(@PathVariable("accountNum") int accountNum) throws ResponseStatusException{
        accountService.deleteAccountByAcctNum(accountNum);
    }

    @PatchMapping("{accountNum}/deposit")
    public Account deposit(@PathVariable("accountNum") int accountNum, @RequestParam double deposit) throws ResponseStatusException {
        return accountService.deposit(accountNum, deposit);
    }

    @PatchMapping("{accountNum}/withdraw")
    public Account withdraw(@PathVariable("accountNum") int accountNum, @RequestParam double withdraw) throws ResponseStatusException {
        return accountService.withdraw(accountNum, withdraw);
    }

    @PatchMapping("/{senderAccountNum}/transfer/{recipientAccountNum}")
    public Account transfer(@PathVariable("senderAccountNum") int senderAcctNum, @PathVariable("recipientAccountNum") int recipientAcctNum, @RequestParam double transfer) throws ResponseStatusException {
        return accountService.transfer(senderAcctNum, recipientAcctNum, transfer);
    }

}
