package com.example.bank.controller;

import com.example.bank.dto.bankAccountDto.AccountDTO;
import com.example.bank.dto.bankAccountDto.BankAccountDTO;
import com.example.bank.dto.bankAccountDto.DepositDTO;
import com.example.bank.dto.bankAccountDto.TransferDTO;
import com.example.bank.entity.BankAccount;
import com.example.bank.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/bank-account")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccountDTO createAccount(@RequestBody AccountDTO accountDTO) {
        return bankAccountService.create(accountDTO);
    }

    @PostMapping("/create-deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccountDTO createDeposit(@RequestBody DepositDTO depositDTO) {
        return bankAccountService.deposit(depositDTO);
    }

    @PostMapping("/withdraw")
    public BankAccountDTO withdraw(@RequestBody AccountDTO accountDTO) {
        return bankAccountService.withdraw(accountDTO);
    }

    @PostMapping("/transfer")
    public BankAccountDTO transfer(@RequestBody TransferDTO transferDTO) {
        return bankAccountService.transfer(transferDTO);
    }
}
