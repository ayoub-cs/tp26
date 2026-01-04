package com.example.pricingservice.web;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Contrôleur simulant un service de tarification
 */
@RestController
@RequestMapping("/api/prices")
public class PricingController {

    /**
     * Retourne le prix d’un livre avec possibilité de simuler des pannes
     */
    @GetMapping("/{bookId}")
    public double getPrice(
            @PathVariable long bookId,
            @RequestParam(name = "fail", defaultValue = "false") boolean forceFailure
    ) {

        // Panne forcée (tests de résilience)
        if (forceFailure) {
            throw new IllegalStateException("Service de tarification indisponible (panne forcée)");
        }

        // Panne aléatoire (30 %)
        if (ThreadLocalRandom.current().nextInt(100) < 30) {
            throw new IllegalStateException("Défaillance aléatoire du service de tarification");
        }

        // Calcul simple du prix basé sur l’identifiant du livre
        return 50.0 + (bookId % 10) * 5.0;
    }
}
