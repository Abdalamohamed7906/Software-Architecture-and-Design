package com.stockcompare.service;

import com.stockcompare.domain.interfaces.IAccountService;
import com.stockcompare.domain.model.UserDetail;
import com.stockcompare.repository.IUserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

/**
 * UserService — implements IAccountService.
 *
 * Matches Create Account sequence diagram:
 *   UI → createAccount(details) → UserService
 *   UserService → validateDetails() [self-loop]
 *   UserService → checkUserExists() → UserRepository
 *   UserService → saveUser()        → UserRepository
 *   UserRepository → success → UserService → accountCreated() → UI
 *
 * Matches Manage Account sequence diagram:
 *   UI → getUserDetails()           → UserService → fetchUser()   → UserRepository
 *   UI → validateUpdatedDetails()   → UserService → updateUser()  → UserRepository
 *
 * SOLID — Single Responsibility: user account business logic only.
 * SOLID — Dependency Inversion: depends on IUserRepository, never on SQLite.
 */
public class UserService implements IAccountService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // createAccount() — called after enterDetails() in sequence diagram
    @Override
    public UserDetail createAccount(String username, String email, String password) {
        UserDetail user = new UserDetail(
                UUID.randomUUID().toString(), username, email, hashPassword(password));

        // validateDetails() — self-loop in sequence diagram
        if (!validateDetails(user))
            throw new IllegalArgumentException("Invalid user details — check username and email.");

        // checkUserExists() → UserRepository
        if (checkUserExists(username, email))
            throw new IllegalStateException("Username or email already registered.");

        // saveUser() → UserRepository → success
        return userRepository.saveUser(user);
    }

    // getUserDetails() — fetchUser() → UserRepository in Manage Account diagram
    @Override
    public UserDetail getAccountDetails(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }

    // updateAccountDetails() — updateUser() → UserRepository in Manage Account diagram
    @Override
    public boolean updateAccountDetails(UserDetail user) {
        if (!validateDetails(user))
            throw new IllegalArgumentException("Invalid updated details.");
        return userRepository.updateUser(user);
    }

    // validateDetails() — self-loop in Create Account diagram
    @Override
    public boolean validateDetails(UserDetail user) {
        if (user == null) return false;
        if (user.username == null || user.username.isBlank()) return false;
        if (user.email == null || !user.email.contains("@")) return false;
        return true;
    }

    // checkUserExists() → UserRepository in Create Account diagram
    @Override
    public boolean checkUserExists(String username, String email) {
        return userRepository.existsByEmail(email)
                || userRepository.existsByUsername(username);
    }

    private String hashPassword(String password) {
        if (password == null || password.length() < 6)
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return Base64.getEncoder().encodeToString(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }
}
