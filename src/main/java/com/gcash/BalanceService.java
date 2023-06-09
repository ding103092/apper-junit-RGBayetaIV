package com.gcash;

public class BalanceService {

    private final AccountRepository accountRepository;

    public BalanceService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountRepository getAccountRepository() {
        return this.accountRepository;
    }

    public void deleteAccount(String id) {
        accountRepository.deleteAccount(id);
    }

    public Double getBalance(String id) {
        Account account = accountRepository.getAccount(id);
        if (account != null) {
            return account.getBalance();
        } else {
            return null;
        }
    }

    public void debit(String id, Double amount) {
        Account account = accountRepository.getAccount(id);
        if (account != null && account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            accountRepository.updateAccount(account);
        } else {
            throw new IllegalArgumentException("ERROR: Insufficient balance or account not found.");
        }
    }

    public void credit(String id, Double amount) {
        Account account = accountRepository.getAccount(id);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            accountRepository.updateAccount(account);
        } else {
            throw new IllegalArgumentException("ERROR: Account not found.");
        }
    }

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
