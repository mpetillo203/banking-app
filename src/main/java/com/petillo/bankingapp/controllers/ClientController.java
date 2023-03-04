package com.petillo.bankingapp.controllers;

import com.petillo.bankingapp.models.Client;
import com.petillo.bankingapp.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable("id") int id) throws ResponseStatusException{
        return clientService.getClientById(id);
    }

    @GetMapping
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @PostMapping
    public Client addClient(@RequestBody Client newClient){
        return clientService.addClient(newClient);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable("id") int id, @RequestBody Client updatedClient) throws ResponseStatusException{
        return clientService.updateClient(id, updatedClient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientById(@PathVariable("id") int id) throws ResponseStatusException {
        clientService.deleteClientById(id);
    }
}
