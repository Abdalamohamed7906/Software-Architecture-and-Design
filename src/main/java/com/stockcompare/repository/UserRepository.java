package com.stockcompare.repository;

import com.stockcompare.data.SQLiteDatabase;
import com.stockcompare.domain.model.UserDetail;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

/**
 * UserRepository — SQLite implementation of IUserRepository.
 * Matches "UserRepository" in your architecture diagram and sequence diagrams:
 *   fetchUser()        → UserRepository (Manage Account diagram)
 *   checkUserExists()  → UserRepository (Create Account diagram)
 *   saveUser()         → UserRepository (Create Account diagram)
 *   updateUser()       → UserRepository (Manage Account diagram)
 *
 * SOLID — Single Responsibility: user persistence only.
 * SOLID — Dependency Inversion: services never import this class directly.
 */
public class UserRepository implements IUserRepository {

    private final SQLiteDatabase db;

    public UserRepository(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public UserDetail saveUser(UserDetail user) {
        if (user.userId == null || user.userId.isBlank())
            user.userId = UUID.randomUUID().toString();

        String sql = "INSERT INTO users (user_id, username, email, password_hash) VALUES (?,?,?,?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, user.userId);
            ps.setString(2, user.username);
            ps.setString(3, user.email);
            ps.setString(4, user.passwordHash);
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("saveUser failed: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<UserDetail> findById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(map(rs));
        } catch (SQLException e) {
            System.err.println("[UserRepository] findById: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDetail> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(map(rs));
        } catch (SQLException e) {
            System.err.println("[UserRepository] findByEmail: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean updateUser(UserDetail user) {
        String sql = "UPDATE users SET email=?, password_hash=? WHERE user_id=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, user.email);
            ps.setString(2, user.passwordHash);
            ps.setString(3, user.userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[UserRepository] updateUser: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(String userId) {
        String sql = "DELETE FROM users WHERE user_id=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[UserRepository] deleteUser: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM users WHERE email=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            return ps.executeQuery().next();
        } catch (SQLException e) { return false; }
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT 1 FROM users WHERE username=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            return ps.executeQuery().next();
        } catch (SQLException e) { return false; }
    }

    private UserDetail map(ResultSet rs) throws SQLException {
        return new UserDetail(
                rs.getString("user_id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password_hash")
        );
    }
}
