package com.stockcompare.domain.interfaces;

import com.stockcompare.domain.model.UserDetail;

/**
 * IAccountService — business interface for UC1 and UC2.
 *
 * Matches your Create Account sequence diagram:
 *   UI → createAccount(details) → UserService → validateDetails() [self-loop]
 *                               → checkUserExists() → UserRepository
 *                               → saveUser() → UserRepository
 *
 * Matches your Manage Account sequence diagram:
 *   UI → getUserDetails() → UserService → fetchUser() → UserRepository
 *   UI → validateUpdatedDetails() → UserService → updateUser() → UserRepository
 *
 * SOLID — Single Responsibility: user account logic only.
 * SOLID — Interface Segregation: not mixed with stock or price concerns.
 * SOLID — Dependency Inversion: UI and services depend on this, not concretions.
 */
public interface IAccountService {
    UserDetail createAccount(String username, String email, String password);
    UserDetail getAccountDetails(String userId);
    boolean    updateAccountDetails(UserDetail user);
    boolean    validateDetails(UserDetail user);
    boolean    checkUserExists(String username, String email);
}
