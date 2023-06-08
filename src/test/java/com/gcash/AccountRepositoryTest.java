package com.gcash;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class AccountRepositoryTest {

    @Test
    void successfulAccountCreationTest() {
        AccountRepository repository = new AccountRepository();
        String accountId = repository.createAccount("Ding",100.00);
        Assertions.assertEquals("Ding",repository.getAccount(accountId).name());
    }

    @Test
    void getAccountTest() {
        AccountRepository repository = new AccountRepository();
        String accountId = repository.createAccount("Ding",100.00);
        Assertions.assertNotNull(repository.getAccount(accountId));
        Assertions.assertEquals(100.0,repository.getAccount(accountId).balance());
        Assertions.assertNull(repository.getAccount("RANDOM_ID"));
    }

    @Test
    void successfulAccountDeletionTest() {
        AccountRepository repository = new AccountRepository();
        String accountId = repository.createAccount("Ding",100.00);
        Assertions.assertNotNull(repository.getAccount(accountId));
        repository.deleteAccount(accountId);
        Assertions.assertNull(repository.getAccount(accountId));
    }

    @Test
    void successfulGetNumberOfAccounts() {
        //Setup and kick
        AccountRepository repository = new AccountRepository();
        String id0 = repository.createAccount("Ding", 89.9);
        String id1 = repository.createAccount("Dong", 81.9);
        String id2 = repository.createAccount("Gnid", 83.9);
        String id3 = repository.createAccount("Regi", 84.9);

        //Verify
        Assertions.assertEquals(4, repository.getNumberOfAccounts());

        //Setup
        repository.deleteAccount(id0);

        //Verify
        Assertions.assertEquals(3, repository.getNumberOfAccounts());
    }
}