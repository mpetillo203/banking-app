package com.petillo.bankingapp.services;

import com.petillo.bankingapp.models.Account;
import com.petillo.bankingapp.models.Client;
import com.petillo.bankingapp.repositories.AccountRepo;
import com.petillo.bankingapp.repositories.ClientRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepo clientRepo;

    @Mock
    private AccountRepo accountRepo;

    List<Client> clientList;

    List<Account> accountList;

    @BeforeEach
    void init(){
        clientList = createClients();
        accountList = new ArrayList<>();
    }

    @Test
    void getClientById_success(){
        //Arrange
        int clientId = 1;
        when(clientRepo.findById(clientId)).thenReturn(Optional.of(clientList.get(0)));

        //Act
        Client result = clientService.getClientById(clientId);

        //Assert
        assertEquals(clientList.get(0), result);
    }

    @Test
    void getClientById_failure(){
        //Arrange
        int clientId = 3;

        //Act + Assert
        assertThrows(ResponseStatusException.class, () -> clientService.getClientById(clientId));
    }

    @Test
    void getAllClients_success(){
        //Arrange
        when(clientRepo.findAll()).thenReturn(clientList);

        //Act
        List<Client> result = clientService.getAllClients();

        //Assert
        assertEquals(clientList, result);
    }

    @Test
    void addClient_success(){
        //Arrange
        Client newClient = clientList.get(0);
        when(clientRepo.save(newClient)).thenReturn(newClient);

        //Act
        Client result = clientService.addClient(newClient);

        //Assert
        assertEquals(newClient, result);
    }

    @Test
    void updateClient_success(){
        //Arrange
        Client updatedClient = new Client(clientList.get(0).getId(), "Mike", "Jordan");
        when(clientRepo.findById(clientList.get(0).getId())).thenReturn(Optional.of(clientList.get(0)));
        when(clientRepo.save(any(Client.class))).thenReturn(updatedClient);

        //Act
        Client result = clientService.updateClient(clientList.get(0).getId(), updatedClient);

        //Assert
        assertEquals(updatedClient, result);
    }

    @Test
    void updateClient_failure(){
        //Arrange
        int clientId = 3;
        Client updatedClient = new Client(clientList.get(0).getId(), "Mike", "Jordan");

        //Act + Assert
        assertThrows(ResponseStatusException.class, () -> clientService.updateClient(clientId, updatedClient));
    }

    @Test
    void deleteClient_success(){
        //Act
        clientService.deleteClientById(clientList.get(0).getId());

        //Assert
        verify(clientRepo).deleteById(clientList.get(0).getId());
    }

    @Test
    void deleteClient_failure(){
        //Arrange
        Account account = new Account(1, 100, "Checking", clientList.get(0).getId());
        accountList.add(account);
        when(accountRepo.findAllByClientId(clientList.get(0).getId())).thenReturn(accountList);

        //Act + Assert
        assertThrows(ResponseStatusException.class, () -> clientService.deleteClientById(clientList.get(0).getId()));

    }



    private List<Client> createClients(){
        Client client1 = new Client(1, "Michael", "Jordan");
        Client client2 = new Client(2, "Larry", "Bird");
        return Arrays.asList(client1, client2);
    }
}
