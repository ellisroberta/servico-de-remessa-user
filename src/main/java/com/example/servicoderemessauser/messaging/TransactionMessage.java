package com.example.servicoderemessauser.messaging;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionMessage {

    private UUID userId;
    private BigDecimal amount;
    private String currency;

    public TransactionMessage(UUID userId, BigDecimal amount, String currency) {
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
    }
}
