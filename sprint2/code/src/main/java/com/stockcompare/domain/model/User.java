package com.stockcompare.domain.model;

/**
 * User — abstract base for all user types.
 *
 * SOLID — Open/Closed: subclasses extend behaviour without modifying this class.
 * SOLID — Liskov Substitution: Visitor, RegisteredUser, Admin can replace User anywhere.
 *
 * Clean Architecture — Entity layer: pure domain object, no framework dependencies.
 */
public abstract class User {
    private final String userId;
    private final String username;

    protected User(String userId, String username) {
        this.userId   = userId;
        this.username = username;
    }

    public String getUserId()   { return userId;   }
    public String getUsername() { return username; }
}
