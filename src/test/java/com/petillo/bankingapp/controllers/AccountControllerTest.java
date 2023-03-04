package com.petillo.bankingapp.controllers;

import com.petillo.bankingapp.models.Account;
import com.petillo.bankingapp.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    List<Account> accountList;

    int accountNum;

    int accountNum2;

    int amount;

    @BeforeEach
    void init(){
        accountList = createAccounts();
        accountNum = accountList.get(0).getAccountNum();
        accountNum2 = accountList.get(1).getAccountNum();
        amount = 100;
    }

    @Test
    void getAccountByAccountNum(){
        accountController.getAccountByAccountNum(accountNum);
        verify(accountService).getAccountByAccountNum(accountNum);
    }

    @Test
    void getAllAccounts(){
        accountController.getAllAccounts();
        verify(accountService).getAllAccounts();
    }

    @Test
    void getAllAccountsByClientId(){
        accountController.getAllAccountsByClientId(accountList.get(0).getClientId());
        verify(accountService).getAllAccountsByClientId(accountList.get(0).getClientId());
    }

    @Test
    void addAccount(){
        accountController.addAccount(new Account());
        verify(accountService).addAccount(new Account());
    }

    @Test
    void updateAccount(){
        accountController.updateAccount(accountNum, new Account());
        verify(accountService).updateAccount(accountNum, new Account());
    }

    @Test
    void deleteAccount(){
        accountController.deleteAccountById(accountNum);
        verify(accountService).deleteAccountByAcctNum(accountNum);
    }

    @Test
    void deposit(){
        accountController.deposit(accountNum, amount);
        verify(accountService).deposit(accountNum, amount);
    }

    @Test
    void withdraw(){
        accountController.withdraw(accountNum, amount);
        verify(accountService).withdraw(accountNum, amount);
    }

    @Test
    void transfer(){
        accountController.transfer(accountNum, accountNum2, amount);
        verify(accountService).transfer(accountNum, accountNum2, amount);
    }


    private List<Account> createAccounts(){
        Account a1 = new Account(1, 100.00, "Checking", 1);
        Account a2 = new Account(2, 12300.00, "Saving", 1);
        Account a3 = new Account(3, 0, "Checking", 2);
        return Arrays.asList(a1, a2, a3);
    }

}
