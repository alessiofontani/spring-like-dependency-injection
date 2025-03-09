package org.client.controller;

import org.client.service.ClientService;
import org.di.Autowired;
import org.di.Component;

@Component
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
        System.out.println("CONTROLLER: CONSTRUCTOR");
    }

    public void findAll() {
        System.out.println("CONTROLLER: FIND ALL");
        clientService.findAll();
    }

}
