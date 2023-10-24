package com.example.bank.service;

import com.example.bank.dto.bankAccountDto.AccountDTO;
import com.example.bank.dto.bankAccountDto.BankAccountDTO;
import com.example.bank.dto.bankAccountDto.DepositDTO;
import com.example.bank.dto.bankAccountDto.TransferDTO;
import com.example.bank.entity.BankAccount;
import com.example.bank.repository.BankAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest
class BankAccountServiceTest {
    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @AfterEach
    void clean() {
        bankAccountRepository.deleteAll();
    }

    @Test
    void testCreate() {
        AccountDTO accountDTO = AccountDTO.builder()
                .name("Ivan")
                .pinCode(123)
                .build();
       BankAccountDTO result =  bankAccountService.create(accountDTO);
        Assertions.assertEquals(accountDTO.getName(), result.getName());
        Assertions.assertNotNull(result);
    }

    @Test
    void testDeposit() {
        AccountDTO accountDTO = AccountDTO.builder()
                .name("Ivan")
                .pinCode(123)
                .build();
        BankAccountDTO bankAccountDTO =  bankAccountService.create(accountDTO);
        DepositDTO depositDTO = DepositDTO.builder()
                .accountNumber(bankAccountDTO.getAccountNumber())
                .pinCode(123)
                .amount(100.00)
                .build();
        BankAccountDTO result = bankAccountService.deposit(depositDTO);
        Assertions.assertEquals(100.00, result.getBalance());
    }

    @Test
    void testWithdraw() {
        AccountDTO accountDTO = AccountDTO.builder()
                .name("Ivan")
                .pinCode(123)
                .build();
        BankAccountDTO bankAccountDTO =  bankAccountService.create(accountDTO);
        DepositDTO depositDTO = DepositDTO.builder()
                .accountNumber(bankAccountDTO.getAccountNumber())
                .pinCode(123)
                .amount(100.00)
                .build();
        accountDTO.setAccountNumber(bankAccountDTO.getAccountNumber());
        BankAccountDTO depositResult = bankAccountService.deposit(depositDTO);
        AccountDTO accountDTO2 = AccountDTO.builder()
                .name(depositResult.getName())
                .pinCode(depositResult.getPinCode())
                .accountNumber(depositResult.getAccountNumber())
                .amount(50.00)
                .build();
        BankAccountDTO result = bankAccountService.withdraw(accountDTO2);
        Assertions.assertEquals(50.00, result.getBalance());
    }

    @Test
    void testTransfer() {
        AccountDTO accountDTO1 = AccountDTO.builder()
                .name("Ivan")
                .pinCode(123)
                .build();
        AccountDTO accountDTO2 = AccountDTO.builder()
                .name("Roma")
                .pinCode(321)
                .build();
        BankAccountDTO bankAccountDTO1 =  bankAccountService.create(accountDTO1);
        BankAccountDTO bankAccountDTO2 =  bankAccountService.create(accountDTO2);
        DepositDTO depositDTO = DepositDTO.builder()
                .accountNumber(bankAccountDTO1.getAccountNumber())
                .pinCode(123)
                .amount(100.00)
                .build();
        BankAccountDTO depositResult = bankAccountService.deposit(depositDTO);
        TransferDTO transferDTO = TransferDTO.builder()
                .amount(50.00)
                .sourсeAccountNumber(depositResult.getAccountNumber())
                .targetAccountNumber(bankAccountDTO2.getAccountNumber())
                .build();
        BankAccountDTO bankAccountDTO = bankAccountService.transfer(transferDTO);
        Assertions.assertEquals(50.00, bankAccountDTO.getBalance());
    }
}