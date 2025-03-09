package org.client;

import org.client.controller.ClientController;
import org.di.ComponentInstanceHolder;
import org.di.DependencyInjectionApp;

public class Main {
    public static void main(String[] args) {
        ComponentInstanceHolder componentInstanceHolder = DependencyInjectionApp.run("org.client");
        ClientController initializedController = componentInstanceHolder.getComponent(ClientController.class);
        initializedController.findAll();
    }

}