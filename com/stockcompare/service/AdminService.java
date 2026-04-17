package com.stockcompare.service;

import java.util.ArrayList;
import java.util.List;

import com.stockcompare.domain.Admin;
import com.stockcompare.domain.interfaces.IAdminService;

public class AdminService implements IAdminService {

    private final List<Admin> admins = new ArrayList<>();

    @Override
    public List<Admin> getAllAdmins() {
        return admins;
    }

    @Override
    public Admin getAdminById(String id) {
        return admins.stream()
                .filter(admin -> admin.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Admin createAdmin(Admin admin) {
        admins.add(admin);
        return admin;
    }

    @Override
    public Admin updateAdmin(String id, Admin updatedAdmin) {
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).getId().equals(id)) {
                admins.set(i, updatedAdmin);
                return updatedAdmin;
            }
        }
        return null;
    }

    @Override
    public boolean deleteAdmin(String id) {
        return admins.removeIf(admin -> admin.getId().equals(id));
    }
}
