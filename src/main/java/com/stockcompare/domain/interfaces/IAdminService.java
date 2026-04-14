package com.stockcompare.domain.interfaces;
import com.stockcompare.domain.model.UserDetail;
import java.util.List;
public interface IAdminService {
    List<UserDetail> getAllUsers();
    UserDetail       getUserByUsername(String username);
    boolean          deleteUser(String userId);
    int              getTotalUserCount();
}
