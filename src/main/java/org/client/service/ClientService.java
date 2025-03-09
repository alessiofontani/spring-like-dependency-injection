package org.client.service;

import org.client.repository.ClientRepository;
import org.di.Autowired;
import org.di.Component;

@Component
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        System.out.println("SERVICE: CONSTRUCTOR");
    }

    public void findAll() {
        System.out.println("SERVICE: FIND ALL");
        clientRepository.findAll();
    }
}
