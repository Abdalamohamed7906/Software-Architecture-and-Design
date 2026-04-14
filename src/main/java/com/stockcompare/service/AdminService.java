package com.stockcompare.service;
import com.stockcompare.domain.interfaces.IAdminService;
import com.stockcompare.domain.model.UserDetail;
import com.stockcompare.repository.IUserRepository;
import java.util.List;
public class AdminService implements IAdminService {
    private final IUserRepository userRepository;
    public AdminService(IUserRepository userRepository) { this.userRepository = userRepository; }
    public List<UserDetail> getAllUsers() { return userRepository.getAllUsers(); }
    public UserDetail getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }
    public boolean deleteUser(String userId) { return userRepository.deleteUser(userId); }
    public int getTotalUserCount() { return userRepository.getAllUsers().size(); }
}
