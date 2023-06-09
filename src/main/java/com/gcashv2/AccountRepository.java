package com.gcashv2;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountRepository {
    private final Map<String, Account> accounts;

    AccountRepository() {
        accounts = new HashMap<>();
    }

    public void createAccount(String id, String name, Double initialBalance) {
        Account account = new Account(id, name, initialBalance);
        accounts.put(id, account);
    }

    public String createAccount(String name, Double initialBalance) {
        String id;
        do {
            id = UUID.randomUUID().toString();
        } while (accounts.containsKey(id));

        Account account = new Account(id, name, initialBalance);
        accounts.put(id, account);
        return id;
    }


    public Account  getAccount(String id) {
        return accounts.get(id);
    }

    public void deleteAccount(String id) {
        accounts.remove(id);
    }

    public Integer getNumberOfAccounts() {
        return accounts.size();
    }

    public boolean noRegisteredAccount() {
        return accounts.isEmpty();
    }
}