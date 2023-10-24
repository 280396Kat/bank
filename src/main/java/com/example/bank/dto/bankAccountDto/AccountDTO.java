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
public class AccountDTO {

    private Long accountNumber;

    private double amount;

    private String name;

    private int pinCode;
}

