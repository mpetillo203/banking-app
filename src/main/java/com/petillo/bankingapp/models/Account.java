package com.petillo.bankingapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountNum;

    private double accountBal;
    private String accountType;
    private int clientId;

    public Account(double accountBal, String accountType, int clientId) {
        this.accountBal = accountBal;
        this.accountType = accountType;
        this.clientId = clientId;
    }
}
