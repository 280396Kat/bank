package com.example.bank.util;

import java.util.UUID;

public class NumberGenerator {

    public static Long generateNumber() {
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        long number = mostSignificantBits ^ leastSignificantBits;
        return Math.abs(number);
    }
}
