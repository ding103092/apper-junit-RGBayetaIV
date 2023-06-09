package com.gcash;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountRepository {
    private final Map<String, Account> accounts;

    AccountRepository() {
        accounts = new HashMap<>();
    }

    /**
     * This method is called when an account is being created.
     * @param name The name of the account
     * @param initialBalance The initial balance of the account
     * @return The id of the account
     */
    public String createAccount(String name, Double initialBalance) {
        String id;
        do {
            id = UUID.randomUUID().toString();
        } while (accounts.containsKey(id));

        Account account = new Account(id, name, initialBalance);
        accounts.put(id, account);
        return id;
    }

    /**
     * @param id The id of the account
     * @return The account with the corresponding id
     */
    public Account  getAccount(String id) {
        return accounts.get(id);
    }

    /**
     * This method is called to update the account in the
     * AccountRepository during debit and credit processing
     * @param account The account to be updated
     */
    public void updateAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    /**
     * @param id The id of the account to be deleted
     */
    public void deleteAccount(String id) {
        accounts.remove(id);
    }

    /**
     * @return The number of registered accounts
     */
    public Integer getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Checks if the account repository is empty
     * @return True if empty, otherwise false
     */
    public boolean noRegisteredAccount() {
        return accounts.isEmpty();
    }
}
