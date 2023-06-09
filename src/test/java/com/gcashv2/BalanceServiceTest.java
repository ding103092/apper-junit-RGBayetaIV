package com.gcashv2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceServiceTest {
    private BalanceService balanceService;
    private String accountId1;
    private String accountId2;
    private String accountId3;

    @BeforeEach
    void setUp() {
        AccountRepository accountRepository = new AccountRepository();
        accountId1 = accountRepository.createAccount("Ding", 500.0);
        accountId2 = accountRepository.createAccount("Dong", 1000.0);
        accountId3 = accountRepository.createAccount("Temp", 69420.0);
        balanceService = new BalanceService(accountRepository);
    }

    @Test
    void successfulAccountCreationDeletionTest() {
        // Handles creation testing
        Assertions.assertEquals("Ding",balanceService.getAccountRepository().getAccount(accountId1).name());
        Assertions.assertNotNull(balanceService.getAccountRepository().getAccount(accountId2));
        Assertions.assertEquals(500.0,balanceService.getBalance(accountId1));
        Assertions.assertNull(balanceService.getAccountRepository().getAccount("RANDOM_ID"));
        Assertions.assertEquals(3,balanceService.getAccountRepository().getNumberOfAccounts());
        Assertions.assertFalse(balanceService.getAccountRepository().noRegisteredAccount());

        // Handles Deletion testing
        balanceService.deleteAccount(accountId3);
    }

    @Test
    void getBalance() {
        assertEquals(500.0, balanceService.getBalance(accountId1));
        assertEquals(1000.0, balanceService.getBalance(accountId2));
        assertNull(balanceService.getBalance("RANDOM_ID"));
    }

    @Test
    void debit() {
        balanceService.debit(accountId1, 200.0);
        Assertions.assertEquals(300.0, balanceService.getBalance(accountId1));
    }

    @Test
    void debitWithInsufficientBalance() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> balanceService.debit(accountId1, 600.0));
        Assertions.assertEquals("ERROR: Insufficient balance or account not found.", exception.getMessage());
    }

    @Test
    void credit() {
        balanceService.credit(accountId1, 200.0);
        Assertions.assertEquals(700.0, balanceService.getBalance(accountId1));

        // Crediting an account that does not exist
        Exception exception = assertThrows(IllegalArgumentException.class, () -> balanceService.credit("RANDOM_ID",200.0));
        Assertions.assertEquals("ERROR: Account not found.", exception.getMessage());
    }

    @Test
    void transfer() {
        balanceService.transfer(accountId2, accountId1, 200.0);
        Assertions.assertEquals(700.0, balanceService.getBalance(accountId1));
        Assertions.assertEquals(800.0, balanceService.getBalance(accountId2));

        // Transferring between accounts where one or both accounts does not exist
        Exception exception = assertThrows(IllegalArgumentException.class, () -> balanceService.transfer("RANDOM_SENDER","RANDOM_RECEIVER",200.0));
        Assertions.assertEquals("ERROR: One or both accounts not found.", exception.getMessage());
    }

    @Test
    void transferWithInsufficientBalance() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            balanceService.transfer(accountId1, accountId2, 600.0);
        });
        Assertions.assertEquals("ERROR: Insufficient balance or account not found.", exception.getMessage());
    }

    @Test
    void noRegisteredAccount() {
        Assertions.assertFalse(balanceService.getAccountRepository().noRegisteredAccount());
        Assertions.assertEquals(3, balanceService.getAccountRepository().getNumberOfAccounts());
        balanceService.deleteAccount(accountId1);
        balanceService.deleteAccount(accountId2);
        balanceService.deleteAccount(accountId3);
        Assertions.assertEquals(0, balanceService.getAccountRepository().getNumberOfAccounts());
        Assertions.assertTrue(balanceService.getAccountRepository().noRegisteredAccount());
    }
}
