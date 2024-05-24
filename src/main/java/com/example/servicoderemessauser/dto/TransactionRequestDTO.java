package com.example.servicoderemessauser.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionRequestDTO {

    private UUID userId;
    private BigDecimal amountBrl;
}
