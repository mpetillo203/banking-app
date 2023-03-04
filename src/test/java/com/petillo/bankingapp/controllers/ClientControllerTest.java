package com.petillo.bankingapp.controllers;

import com.petillo.bankingapp.models.Client;
import com.petillo.bankingapp.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    List<Client> clientList;

    int clientId;

    @BeforeEach
    void init(){
        clientList = createClients();
        clientId = clientList.get(0).getId();
    }

    @Test
    void getClientById(){
        clientController.getClientById(clientId);
        verify(clientService).getClientById(clientId);
    }

    @Test
    void getAllClients(){
        clientController.getAllClients();
        verify(clientService).getAllClients();
    }

    @Test
    void addClient(){
        clientController.addClient(new Client());
        verify(clientService).addClient(new Client());
    }

    @Test
    void updateClient(){
        clientController.updateClient(clientId, new Client());
        verify(clientService).updateClient(clientId, new Client());
    }

    @Test
    void deleteClient(){
        clientController.deleteClientById(clientId);
        verify(clientService).deleteClientById(clientId);
    }



    private List<Client> createClients(){
        Client client1 = new Client(1, "Michael", "Jordan");
        Client client2 = new Client(2, "Larry", "Bird");
        return Arrays.asList(client1, client2);
    }

}
