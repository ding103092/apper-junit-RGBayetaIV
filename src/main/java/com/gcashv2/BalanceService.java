package com.gcashv2;

public record BalanceService(AccountRepository accountRepository) {

    /**
     * Deletes the account in the accountRepository
     *
     * @param id The id of the account to be deleted
     */
    public void deleteAccount(String id) {
        accountRepository.deleteAccount(id);
    }

    /**
     * Get the balance of the account with the specified id
     *
     * @param id The id of the account that will get the balance
     * @return The current balance of the account with id
     */
    public Double getBalance(String id) {
        Account account = accountRepository.getAccount(id);
        if (account != null) {
            return account.balance();
        } else {
            return null;
        }
    }

    /**
     * Updates the balance of an account with the specified id and deducts the amount
     * from its current balance. Since the account is a record, we have to delete
     * the old account, and create a new account with the same id and its updated balance
     *
     * @param id     The id of the account to be debited
     * @param amount The amount to be deducted from the account
     */
    public void debit(String id, Double amount) {
        Account account = accountRepository.getAccount(id);
        if (account != null && account.balance() >= amount) {
            accountRepository.deleteAccount(id);
            accountRepository.createAccount(id, account.name(), account.balance() - amount);
        } else {
            throw new IllegalArgumentException("ERROR: Insufficient balance or account not found.");
        }
    }

    /**
     * Updates the balance of the account with the specified id and adds the amount
     * from its current balance. Since the account is a record, we have to delete
     * the old account, and create a new account with the same id, and its updated balance
     *
     * @param id     The id of the account to be credited
     * @param amount The amount to be credited
     */
    public void credit(String id, Double amount) {
        Account account = accountRepository.getAccount(id);
        if (account != null) {
            accountRepository.deleteAccount(id);
            accountRepository.createAccount(id, account.name(), account.balance() + amount);
        } else {
            throw new IllegalArgumentException("ERROR: Account not found.");
        }
    }

    /**
     * This method calls debit, credit to transfer funds from a sender to a receiver
     * @param from   The id of the sender account
     * @param to     The id of the receiver account
     * @param amount The amount to be transferred
     */
    public void transfer(String from, String to, Double amount) {
        Account accountFrom = accountRepository.getAccount(from);
        Account accountTo = accountRepository.getAccount(to);

        if (accountFrom != null && accountTo != null) {
            debit(from, amount);
            credit(to, amount);
        } else {
            throw new IllegalArgumentException("ERROR: One or both accounts not found.");
        }
    }
}
