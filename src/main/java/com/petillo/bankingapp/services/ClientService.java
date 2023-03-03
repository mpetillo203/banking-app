package com.petillo.bankingapp.services;

import com.petillo.bankingapp.models.Client;
import com.petillo.bankingapp.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepo clientRepo;

    private final String itemNotFound = "Client not found.";

    @Autowired
    public ClientService(ClientRepo clientRepo){
        this.clientRepo = clientRepo;
    }

    public Client getClientById(String id){
        return clientRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, itemNotFound));
    }

    public List<Client> getAllClients(){
        return clientRepo.findAll();
    }

    public Client addClient(Client newClient){
        return clientRepo.save(newClient);
    }

    public Client updateClient(Client updatedClient){
        return clientRepo.save(updatedClient);
    }

    //Add a check so that clients who have accts cannot be deleted
    public void deleteClientById(String id){
        clientRepo.deleteById(id);
    }

}
