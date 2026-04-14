package com.stockcompare.repository;
import com.stockcompare.domain.model.*;
import java.util.List;
import java.util.Optional;
public interface IUserRepository {
    UserDetail           saveUser(UserDetail user);
    Optional<UserDetail> findById(String userId);
    Optional<UserDetail> findByEmail(String email);
    Optional<UserDetail> findByUsername(String username);
    List<UserDetail>     getAllUsers();
    boolean              updateUser(UserDetail user);
    boolean              deleteUser(String userId);
    boolean              existsByEmail(String email);
    boolean              existsByUsername(String username);
}
