package com.example.bookservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PricingClient {

    private final RestTemplate restTemplate;
    private final String pricingBaseUrl;

    public PricingClient(RestTemplate restTemplate,
                         @Value("${pricing.base-url}") String pricingBaseUrl) {
        this.restTemplate = restTemplate;
        this.pricingBaseUrl = pricingBaseUrl;
    }

    /**
     * Récupère le prix d’un livre depuis le service Pricing
     * avec mécanismes de retry et circuit breaker
     */
    @Retry(name = "pricing")
    @CircuitBreaker(name = "pricing", fallbackMethod = "defaultPrice")
    public double fetchPrice(long bookId) {
        String endpoint = pricingBaseUrl + "/api/prices/" + bookId;
        Double price = restTemplate.getForObject(endpoint, Double.class);

        return price != null ? price : 0.0;
    }

    /**
     * Méthode de secours appelée en cas d’échec du service Pricing
     */
    public double defaultPrice(long bookId, Throwable throwable) {
        return 0.0;
    }
}
