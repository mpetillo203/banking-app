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

    public Account getAccountByAccountNum(int accountNum) throws ResponseStatusException{
        return accountRepo.findById(accountNum).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, itemNotFound));
    }

    public List<Account> getAllAccounts(){
        return accountRepo.findAll();
    }

    public List<Account> getAllAccountsByClientId(int clientId) {
        return accountRepo.findAllByClientId(clientId);
    }

    public Account addAccount(Account newAcct){
        return accountRepo.save(newAcct);
    }

    public Account updateAccount(int accountNum, Account updatedAcct) throws ResponseStatusException {
        return accountRepo.findById(accountNum)
                .map(account -> {
                    account.setAccountBal(updatedAcct.getAccountBal());
                    account.setAccountType(updatedAcct.getAccountType());
                    account.setClientId(updatedAcct.getClientId());
                    return accountRepo.save(account);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, itemNotFound));
    }

    public void deleteAccountByAcctNum(int accountNum){
        Account account = getAccountByAccountNum(accountNum);
        if (account.getAccountBal() > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account cannot be deleted because it has a positive balance.");
        }
        accountRepo.deleteById(accountNum);
    }

    public Account deposit(int accountNum, double deposit) throws ResponseStatusException{
        Account account = getAccountByAccountNum(accountNum);
        if(deposit <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid deposit. Amount must be greater than $0.");
        }
        double currentBal = account.getAccountBal();
        double newBal = currentBal + deposit;
        account.setAccountBal(newBal);
        return accountRepo.save(account);
    }

    public Account withdraw(int accountNum, double withdraw) throws ResponseStatusException{
        Account account = getAccountByAccountNum(accountNum);
        double currentBal = account.getAccountBal();
        if(withdraw > currentBal){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid withdrawal attempt. Insufficient funds");
        } else if (withdraw <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid withdrawal. Amount must be greater than $0.");
        }
        double newBal = currentBal - withdraw;
        account.setAccountBal(newBal);
        return accountRepo.save(account);
    }

    public Account transfer(int senderAcctNum, int recipientAcctNum, double transferAmt) throws ResponseStatusException{
        Account senderAcct = getAccountByAccountNum(senderAcctNum);
        Account recipientAcct = getAccountByAccountNum(recipientAcctNum);
        double senderBal = senderAcct.getAccountBal();
        double recipientBal = recipientAcct.getAccountBal();
        if (senderBal < transferAmt){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transfer attempt. Insufficient funds.");
        } else if (transferAmt <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transfer. Amount must be greater than $0.");
        }
        double senderNewBal = senderBal - transferAmt;
        double recipientNewBal = recipientBal + transferAmt;
        senderAcct.setAccountBal(senderNewBal);
        recipientAcct.setAccountBal(recipientNewBal);
        return accountRepo.save(senderAcct);
    }
}
