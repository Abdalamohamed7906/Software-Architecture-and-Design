package com.stockcompare.domain.interfaces;

import java.util.List;
import com.stockcompare.domain.Admin;

public interface IAdminService {

    List<Admin> getAllAdmins();

    Admin getAdminById(String id);

    Admin createAdmin(Admin admin);

    Admin updateAdmin(String id, Admin admin);

    boolean deleteAdmin(String id);
}
