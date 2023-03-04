package com.petillo.bankingapp.services;

import com.petillo.bankingapp.models.Account;
import com.petillo.bankingapp.models.Client;
import com.petillo.bankingapp.repositories.AccountRepo;
import com.petillo.bankingapp.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepo clientRepo;
    private final AccountRepo accountRepo;

    private final String itemNotFound = "Client not found.";

    @Autowired
    public ClientService(ClientRepo clientRepo, AccountRepo accountRepo){
        this.clientRepo = clientRepo;
        this.accountRepo = accountRepo;
    }

    public Client getClientById(int id) throws ResponseStatusException{
        return clientRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, itemNotFound));
    }

    public List<Client> getAllClients(){
        return clientRepo.findAll();
    }

    public Client addClient(Client newClient){
        return clientRepo.save(newClient);
    }

    public Client updateClient(int id, Client updatedClient) throws ResponseStatusException{
        return clientRepo.findById(id)
                .map(client -> {
                    client.setFName(updatedClient.getFName());
                    client.setLName(updatedClient.getLName());
                    return clientRepo.save(client);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, itemNotFound));
    }

    public void deleteClientById(int id) throws ResponseStatusException{
        List<Account> clientAccountList = accountRepo.findAllByClientId(id);
        if (!clientAccountList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client has active accounts. Deletion not allowed.");
        }
        clientRepo.deleteById(id);
    }

}
