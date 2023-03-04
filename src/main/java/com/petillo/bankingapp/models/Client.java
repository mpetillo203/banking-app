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
public class Client {

    //need to auto generate ids for posting new items
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fName;
    private String lName;

    public Client(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }
}
