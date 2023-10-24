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
public class BankAccountDTO {

    private Long id;

    private Long accountNumber;

    private String name;

    private int pinCode;

    private double balance;
}
