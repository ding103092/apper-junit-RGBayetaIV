package com.gcashv2;

/**
 * Account class implements record, which is
 * immutable.
 * @param id Unique identifier for the account
 * @param name Name of the account
 * @param balance Current balance of the account
 */
public record Account(String id, String name, Double balance) {
}