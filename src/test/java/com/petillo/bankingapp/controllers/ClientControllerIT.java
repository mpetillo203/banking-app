package com.petillo.bankingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petillo.bankingapp.models.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientController.class)
@ActiveProfiles("test")
public class ClientControllerIT {

    @MockBean
    private ClientController clientController;

    private List<Client> clientList;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
        clientList = createClients();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllClients_returns200() throws Exception{
        mockMvc
                .perform(get("/clients"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getClientById_returns200() throws Exception{
        mockMvc
                .perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void addClient_returns200() throws Exception{
        mockMvc
            .perform(post("/clients")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(clientList.get(0))))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateClient_returns200() throws Exception{
        mockMvc
            .perform(put("/clients/" + clientList.get(0).getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(clientList.get(0))))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteClient_returns204() throws Exception{
        mockMvc
            .perform(delete("/clients/" + clientList.get(0).getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(clientList.get(0))))
                .andExpect(status().isNoContent())
                .andReturn();

    }

    private List<Client> createClients(){
        Client client1 = new Client(1, "Michael", "Jordan");
        Client client2 = new Client(2, "Larry", "Bird");
        return Arrays.asList(client1, client2);
    }
}
