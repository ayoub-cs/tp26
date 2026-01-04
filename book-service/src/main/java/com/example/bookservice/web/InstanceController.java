package com.example.bookservice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint de diagnostic permettant d’identifier l’instance active
 */
@RestController
public class InstanceController {

    @Value("${server.port}")
    private int serverPort;

    /**
     * Retourne les informations de l’instance (hôte + port interne)
     */
    @GetMapping("/api/debug/instance")
    public String getInstanceInfo() {
        String hostname = System.getenv().getOrDefault("HOSTNAME", "local");
        return "instance=" + hostname + " internalPort=" + serverPort;
    }
}
