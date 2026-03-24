package com.stockcompare.domain.model;

/** RegisteredUser — can save/load/delete stocks, manage account, export data. */
public class RegisteredUser extends User {
    private String email;
    private String passwordHash;

    public RegisteredUser(String userId, String username,
                          String email, String passwordHash) {
        super(userId, username);
        this.email        = email;
        this.passwordHash = passwordHash;
    }

    public String getEmail()                { return email;        }
    public void   setEmail(String email)    { this.email = email;  }
    public String getPasswordHash()         { return passwordHash; }
    public void   setPasswordHash(String h) { this.passwordHash=h; }
}
