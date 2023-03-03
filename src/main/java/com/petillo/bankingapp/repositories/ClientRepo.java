package com.petillo.bankingapp.repositories;

import com.petillo.bankingapp.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<Client, String> {


}
