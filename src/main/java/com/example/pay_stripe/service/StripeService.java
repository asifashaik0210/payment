package com.example.pay_stripe.service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public String createPaymentIntent(int amount, String currency) throws StripeException {
        // Convert the currency code to lowercase
        String lowercaseCurrency = currency.toLowerCase();

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", lowercaseCurrency);

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return paymentIntent.getClientSecret();
    }

}
