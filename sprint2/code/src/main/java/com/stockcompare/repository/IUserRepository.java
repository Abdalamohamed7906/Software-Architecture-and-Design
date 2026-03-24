package com.stockcompare.repository;

import com.stockcompare.domain.model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * IUserRepository — matches UserRepository in your architecture diagram.
 * SOLID — Dependency Inversion: services depend on this interface, not SQLite.
 */
public interface IUserRepository {
    UserDetail           saveUser(UserDetail user);
    Optional<UserDetail> findById(String userId);
    Optional<UserDetail> findByEmail(String email);
    boolean              updateUser(UserDetail user);
    boolean              deleteUser(String userId);
    boolean              existsByEmail(String email);
    boolean              existsByUsername(String username);
}
