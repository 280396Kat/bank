package com.example.bank.entity;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pinCode", nullable = false)
    private int pinCode;

    @Column(name = "balance")
    private double balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber);
    }
}
