package com.stockcompare.domain.model;

/**
 * UserDetail — Data Transfer Object.
 * Matches the UserDetail type used in ICreateAccount and IManageAccount
 * system interfaces shown in your diagrams.
 */
public class UserDetail {
    public String userId;
    public String username;
    public String email;
    public String passwordHash;

    public UserDetail() {}

    public UserDetail(String userId, String username,
                      String email, String passwordHash) {
        this.userId       = userId;
        this.username     = username;
        this.email        = email;
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "UserDetail{username='" + username + "', email='" + email + "'}";
    }
}
