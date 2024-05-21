package com.example.servicoderemessauser.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TransactionMessage {

    private UUID userId;
    private BigDecimal amount;
    private String currency;
}
