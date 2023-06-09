package com.gcash;

/**
 * This Account class uses standard class implementation
 * with getters and setters
 */
public class Account {
    private final String id;
    private final String name;
    private Double balance;

    public Account(String id, String name, Double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("ERROR: Initial balance cannot be negative.");
        }
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("ERROR: Balance cannot be negative.");
        }
        this.balance = balance;
    }
}