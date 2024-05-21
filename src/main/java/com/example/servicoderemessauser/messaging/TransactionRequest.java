package com.example.servicoderemessauser.messaging;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionRequest {

    private UUID userId;
    private BigDecimal amount;
    private String currency;
}
