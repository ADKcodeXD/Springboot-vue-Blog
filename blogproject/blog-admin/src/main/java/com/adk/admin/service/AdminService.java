package com.adk.admin.service;

import com.adk.admin.dao.pojo.Admin;
import com.adk.admin.dao.pojo.Permission;

import java.util.List;

public interface AdminService {

    Admin findAdminByUserName(String username);

    List<Permission> findPermissionByAdminId(Long id);
}
