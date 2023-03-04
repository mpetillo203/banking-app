package com.petillo.bankingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petillo.bankingapp.models.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
@ActiveProfiles("test")
public class AccountControllerIT {

    @MockBean
    private AccountController accountController;

    private List<Account> accountList;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        accountList = createAccounts();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllAccounts_returns200() throws Exception{
        mockMvc
                .perform(get("/accounts"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getAccountById_returns200() throws Exception{
        mockMvc
                .perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getAllAccountsByClientId_returns200() throws Exception{
        mockMvc
            .perform(get("/accounts/clients/1"))
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    void addAccount_returns200() throws Exception{
        mockMvc
            .perform(post("/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(accountList.get(0))))
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    void updateAccount_returns200() throws Exception {
        mockMvc
                .perform(put("/accounts/" + accountList.get(0).getAccountNum())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountList.get(0))))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteAccount_returns204() throws Exception{
        mockMvc
            .perform(delete("/accounts/" + accountList.get(0).getAccountNum())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(accountList.get(0))))
            .andExpect(status().isNoContent())
            .andReturn();
    }

    @Test
    void deposit_returns200() throws Exception{
        mockMvc
                .perform(patch("/accounts/" + accountList.get(0).getAccountNum() + "/deposit")
                        .param("deposit", "100"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void withdraw_returns200() throws Exception{
        mockMvc
                .perform(patch("/accounts/" + accountList.get(0).getAccountNum() + "/withdraw")
                        .param("withdraw", "100"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void transfer_returns200() throws Exception{
        mockMvc
                .perform(patch("/accounts/" + accountList.get(0).getAccountNum() + "/transfer/" + accountList.get(1).getAccountNum())
                        .param("transfer", "100"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private List<Account> createAccounts(){
        Account a1 = new Account(1, 100.00, "Checking", 1);
        Account a2 = new Account(2, 12300.00, "Saving", 1);
        Account a3 = new Account(3, 0, "Checking", 2);
        return Arrays.asList(a1, a2, a3);
    }
}
