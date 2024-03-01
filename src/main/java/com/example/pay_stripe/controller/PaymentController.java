package com.example.pay_stripe.controller;


import com.example.pay_stripe.service.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestParam int amount, @RequestParam String currency) {
        try {
            String clientSecret = stripeService.createPaymentIntent(amount, currency);

            // Using HashMap constructor to create a map
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("clientSecret", clientSecret);

            return ResponseEntity.ok(responseMap);
        } catch (StripeException e) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorMap);
        }
    }
}

