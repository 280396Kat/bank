package com.example.bank.dto.bankAccountDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {

    private Long sourсeAccountNumber;

    private Long targetAccountNumber;

    private int pinCode;

    private double amount;
}
