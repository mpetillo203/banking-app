package com.petillo.bankingapp.config;

import com.petillo.bankingapp.models.Account;
import com.petillo.bankingapp.models.Client;
import com.petillo.bankingapp.repositories.AccountRepo;
import com.petillo.bankingapp.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbSeeder implements CommandLineRunner {

    private final AccountRepo accountRepo;
    private final ClientRepo clientRepo;

    @Autowired
    public DbSeeder(AccountRepo accountRepo, ClientRepo clientRepo){
        this.accountRepo = accountRepo;
        this.clientRepo = clientRepo;
    }

    @Override
    public void run(String... args) throws Exception{

        //Account Seeding
        Account a1 = new Account(1, 100.00, "Checking", "client1");
        Account a2 = new Account(2, 12300.00, "Saving", "client1");
        Account a3 = new Account(3, 500.00, "Checking", "client2");

        this.accountRepo.save(a1);
        this.accountRepo.save(a2);
        this.accountRepo.save(a3);

        //Client Seeding
        Client c1 = new Client("123", "Michael", "Jordan");
        Client c2 = new Client("456", "Larry", "Bird");

        this.clientRepo.save(c1);
        this.clientRepo.save(c2);


    }

}
