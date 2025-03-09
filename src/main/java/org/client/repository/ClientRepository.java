package org.client.repository;

import org.di.Component;

@Component
public class ClientRepository {

    public ClientRepository() {
        System.out.println("REPOSITORY: CONSTRUCTOR");
    }

    public void findAll() {
        System.out.println("REPOSITORY: FIND ALL");
    }


}
