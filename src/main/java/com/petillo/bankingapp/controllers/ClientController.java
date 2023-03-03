package com.petillo.bankingapp.controllers;

import com.petillo.bankingapp.models.Client;
import com.petillo.bankingapp.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Client getClientById(@PathVariable String id){
        return clientService.getClientById(id);
    }

    @GetMapping
    public List<Client> getAllClients(){
        System.out.println("in the client controller get all method");
        return clientService.getAllClients();
    }

    @PostMapping
    public Client addClient(@RequestBody Client newClient){
        System.out.println("in the client controller post method");
        return clientService.addClient(newClient);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable String id, @RequestBody Client updatedClient){
        return clientService.updateClient(updatedClient);
    }

    @DeleteMapping("/{id}")
    public void deleteClientById(@PathVariable String id){
        clientService.deleteClientById(id);
    }
}
