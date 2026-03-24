package com.stockcompare.domain.model;

/** Admin — elevated privileges. Can trigger Update Stored Stock Data (UC11). */
public class Admin extends RegisteredUser {
    public Admin(String userId, String username,
                 String email, String passwordHash) {
        super(userId, username, email, passwordHash);
    }
}
