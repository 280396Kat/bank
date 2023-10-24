package com.example.bank.service;

import com.example.bank.dto.bankAccountDto.AccountDTO;
import com.example.bank.dto.bankAccountDto.BankAccountDTO;
import com.example.bank.dto.bankAccountDto.DepositDTO;
import com.example.bank.dto.bankAccountDto.TransferDTO;
import com.example.bank.entity.BankAccount;
import com.example.bank.repository.BankAccountRepository;
import com.example.bank.util.NumberGenerator;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccountDTO create(AccountDTO accountDTO) {
        BankAccount bankAccountBuilder = BankAccount.builder()
                .name(accountDTO.getName())
                .pinCode(accountDTO.getPinCode())
                .accountNumber(NumberGenerator.generateNumber())
                .balance(0.0)
                .build();
        BankAccount entity = bankAccountRepository.save(bankAccountBuilder);
        BankAccountDTO bankAccountDTO = new BankAccountDTO(entity.getId(), entity.getAccountNumber(),
                entity.getName(), entity.getPinCode(), entity.getBalance());
        return bankAccountDTO;
    }

    public BankAccountDTO deposit(DepositDTO depositDTO) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(depositDTO.getAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Account is not exists"));
        double newBalance = 0.0;
        if (bankAccount.getPinCode() == depositDTO.getPinCode()) {
            newBalance = bankAccount.getBalance() + depositDTO.getAmount();
            bankAccount.setBalance(newBalance);
        }
        BankAccount entity = bankAccountRepository.save(bankAccount);
        BankAccountDTO bankAccountDTO = new BankAccountDTO(entity.getId(), entity.getAccountNumber(),
                entity.getName(), entity.getPinCode(), entity.getBalance());
        return bankAccountDTO;
    }

    public BankAccountDTO withdraw(AccountDTO accountDTO) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountDTO.getAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Account is not exists"));
        double newBalance = 0.0;
        if (bankAccount.getPinCode() == accountDTO.getPinCode()
                && bankAccount.getBalance() >= 0) {
            newBalance = bankAccount.getBalance() - accountDTO.getAmount();
            bankAccount.setBalance(newBalance);
        }
        BankAccount entity = bankAccountRepository.save(bankAccount);
        BankAccountDTO bankAccountDTO = new BankAccountDTO(entity.getId(), entity.getAccountNumber(),
                entity.getName(), entity.getPinCode(), entity.getBalance());
        return bankAccountDTO;
    }

    @Transactional
    public BankAccountDTO transfer(TransferDTO transferDTO) {
        BankAccount sourceAccount = bankAccountRepository.findByAccountNumber(transferDTO.getSourсeAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Account is not exists"));
        BankAccount targetAccount = bankAccountRepository.findByAccountNumber(transferDTO.getTargetAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Account is not exists"));
        if (sourceAccount.getPinCode() == transferDTO.getPinCode()) {
            double sourceNewBalance = sourceAccount.getBalance() - transferDTO.getAmount();
            double targetNewBalance = targetAccount.getBalance() + transferDTO.getAmount();
            sourceAccount.setBalance(sourceNewBalance);
            targetAccount.setBalance(targetNewBalance);
            bankAccountRepository.save(sourceAccount);
            BankAccount bankAccount = bankAccountRepository.save(targetAccount);
            BankAccountDTO bankAccountDTO = new BankAccountDTO(bankAccount.getId(), bankAccount.getAccountNumber(),
                    bankAccount.getName(), bankAccount.getPinCode(), bankAccount.getBalance());
            return bankAccountDTO;
        }
        return null;
    }
}
