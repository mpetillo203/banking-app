package com.petillo.bankingapp.services;

import com.petillo.bankingapp.models.Account;
import com.petillo.bankingapp.repositories.AccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepo accountRepo;

    List<Account> accountList;

    @BeforeEach
    void init(){
        accountList = createAccounts();
    }

    @Test
    void getAccountByAccountNum_success(){
        //Arrange
        int accountNum = accountList.get(0).getAccountNum();
        when(accountRepo.findById(accountNum)).thenReturn(Optional.of(accountList.get(0)));

        //Act
        Account result = accountService.getAccountByAccountNum(accountNum);

        //Assert
        assertEquals(accountList.get(0), result);
    }

    @Test
    void getAccountByAccountNum_failure(){
        //Arrange
        int accountNum = 10;

        //Act + Assert
        assertThrows(ResponseStatusException.class, () -> accountService.getAccountByAccountNum(accountNum));
    }

    @Test
    void getAllAccounts(){
        //Arrange
        when(accountRepo.findAll()).thenReturn(accountList);

        //Act
        List<Account> result = accountService.getAllAccounts();

        //Assert
        assertEquals(accountList, result);
    }

    @Test
    void getAccountsByClientId_success(){
        //Arrange
        int clientId = 1;
        List<Account> clientAccountList = List.of(accountList.get(0), accountList.get(1));
        when(accountRepo.findAllByClientId(clientId)).thenReturn(clientAccountList);

        //Act
        List<Account> result = accountService.getAllAccountsByClientId(clientId);

        //Assert
        assertEquals(clientAccountList, result);
    }

    @Test
    void createAccount(){
        //Arrange
        Account newAccount = accountList.get(0);
        when(accountRepo.save(newAccount)).thenReturn(newAccount);

        //Act
        Account result = accountService.addAccount(newAccount);

        //Assert
        assertEquals(newAccount, result);
    }

    @Test
    void updateAccount_success(){
        //Arrange
        Account updatedAccount = new Account(accountList.get(0).getAccountNum(), 100, "Savings", 2);
        when(accountRepo.findById(accountList.get(0).getAccountNum())).thenReturn(Optional.of(accountList.get(0)));
        when(accountRepo.save(any(Account.class))).thenReturn(updatedAccount);

        //Act
        Account result = accountService.updateAccount(accountList.get(0).getAccountNum(), updatedAccount);

        //Assert
        assertEquals(updatedAccount, result);
    }

    @Test
    void updateClient_failure(){
        //Arrange
        int accountNum = 10;
        Account updatedAccount = new Account(accountList.get(0).getAccountNum(), 100, "Savings", 2);

        //Act + Assert
        assertThrows(ResponseStatusException.class, () -> accountService.updateAccount(accountNum, updatedAccount));
    }

    @Test
    void deleteClient_success(){
        //Arrange
        int accountNum = accountList.get(2).getAccountNum();
        when(accountRepo.findById(accountNum)).thenReturn(Optional.of(accountList.get(2)));

        //Act
        accountService.deleteAccountByAcctNum(accountNum);

        //Assert
        verify(accountRepo).deleteById(accountList.get(2).getAccountNum());
    }

    @Test
    void deleteClient_failure(){
        //Arrange
        int accountNum = accountList.get(0).getAccountNum();


        //Act + Assert
        assertThrows(ResponseStatusException.class, () -> accountService.deleteAccountByAcctNum(accountList.get(0).getAccountNum()));
    }

    @Test
    void deposit_success(){
        //Arrange
        int deposit = 100;
        Account account = accountList.get(0);
        double currentBal = account.getAccountBal();
        account.setAccountBal(currentBal + deposit);
        when(accountRepo.findById(account.getAccountNum())).thenReturn(Optional.of(account));
        when(accountRepo.save(accountList.get(0))).thenReturn(account);

        //Act
        Account result = accountService.deposit(account.getAccountNum(), deposit);

        //Assert
        assertEquals(account, result);
    }

    @Test
    void deposit_failure(){
        //Arrange
        int deposit = -1;
        Account account = accountList.get(0);
        double currentBal = account.getAccountBal();
        account.setAccountBal(currentBal + deposit);
        when(accountRepo.findById(account.getAccountNum())).thenReturn(Optional.of(account));

        //Act + Assert
        assertThrows(ResponseStatusException.class, () -> accountService.deposit(account.getAccountNum(), deposit));
    }

    @Test
    void withdraw_success(){
        //Arrange
        int withdraw = 50;
        Account account = accountList.get(0);
        double currentBal = account.getAccountBal();
        account.setAccountBal(currentBal - withdraw);
        when(accountRepo.findById(account.getAccountNum())).thenReturn(Optional.of(account));
        when(accountRepo.save(accountList.get(0))).thenReturn(account);

        //Act
        Account result = accountService.withdraw(account.getAccountNum(), withdraw);

        //Assert
        assertEquals(account, result);
    }

    @Test
    void withdraw_failureInsufficientFunds(){
        //Arrange
        int withdraw = 200;
        Account account = accountList.get(0);
        double currentBal = account.getAccountBal();
        account.setAccountBal(currentBal - withdraw);
        when(accountRepo.findById(account.getAccountNum())).thenReturn(Optional.of(account));

        //Act + Assert
        assertThrows(ResponseStatusException.class, () -> accountService.withdraw(account.getAccountNum(), withdraw));
    }

    @Test
    void withdraw_failureInvalidInput(){
        //Arrange
        int withdraw = -1;
        Account account = accountList.get(0);
        double currentBal = account.getAccountBal();
        account.setAccountBal(currentBal - withdraw);
        when(accountRepo.findById(account.getAccountNum())).thenReturn(Optional.of(account));

        //Act + Assert
        assertThrows(ResponseStatusException.class, () -> accountService.withdraw(account.getAccountNum(), withdraw));
    }

    @Test
    void transfer_success(){
        //Arrange
        int transfer = 50;
        Account senderAcct = accountList.get(0);
        Account recipientAcct = accountList.get(1);
        double senderCurrentBal = senderAcct.getAccountBal();
        double recipientCurrentBal = recipientAcct.getAccountBal();
        recipientAcct.setAccountBal(recipientCurrentBal + transfer);
        senderAcct.setAccountBal(senderCurrentBal - transfer);
        when(accountRepo.findById(senderAcct.getAccountNum())).thenReturn(Optional.of(senderAcct));
        when(accountRepo.findById(recipientAcct.getAccountNum())).thenReturn(Optional.of(recipientAcct));
        when(accountRepo.save(senderAcct)).thenReturn(senderAcct);

        //Act
        Account result = accountService.transfer(senderAcct.getAccountNum(), recipientAcct.getAccountNum(), transfer);

        //Assert
        assertEquals(senderAcct, result);
    }

    @Test
    void transfer_failureInsufficientFunds(){
        //Arrange
        int transfer = 500;
        Account senderAcct = accountList.get(0);
        Account recipientAcct = accountList.get(1);
        double senderCurrentBal = senderAcct.getAccountBal();
        double recipientCurrentBal = recipientAcct.getAccountBal();
        recipientAcct.setAccountBal(recipientCurrentBal + transfer);
        senderAcct.setAccountBal(senderCurrentBal - transfer);
        when(accountRepo.findById(senderAcct.getAccountNum())).thenReturn(Optional.of(senderAcct));
        when(accountRepo.findById(recipientAcct.getAccountNum())).thenReturn(Optional.of(recipientAcct));

        //Act + Assert
        assertThrows(ResponseStatusException.class, () -> accountService.transfer(senderAcct.getAccountNum(), recipientAcct.getAccountNum(), transfer));
    }

    @Test
    void transfer_failureInvalidInput(){
        //Arrange
        int transfer = -1;
        Account senderAcct = accountList.get(0);
        Account recipientAcct = accountList.get(1);
        double senderCurrentBal = senderAcct.getAccountBal();
        double recipientCurrentBal = recipientAcct.getAccountBal();
        recipientAcct.setAccountBal(recipientCurrentBal + transfer);
        senderAcct.setAccountBal(senderCurrentBal - transfer);
        when(accountRepo.findById(senderAcct.getAccountNum())).thenReturn(Optional.of(senderAcct));
        when(accountRepo.findById(recipientAcct.getAccountNum())).thenReturn(Optional.of(recipientAcct));

        //Act + Assert
        assertThrows(ResponseStatusException.class, () -> accountService.transfer(senderAcct.getAccountNum(), recipientAcct.getAccountNum(), transfer));
    }



    private List<Account> createAccounts(){
        Account a1 = new Account(1, 100.00, "Checking", 1);
        Account a2 = new Account(2, 12300.00, "Saving", 1);
        Account a3 = new Account(3, 0, "Checking", 2);
        return Arrays.asList(a1, a2, a3);
    }
}
