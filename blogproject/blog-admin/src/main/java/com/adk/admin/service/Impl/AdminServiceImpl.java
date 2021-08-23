package com.adk.admin.service.Impl;

import com.adk.admin.dao.mapper.AdminMapper;
import com.adk.admin.dao.pojo.Admin;
import com.adk.admin.dao.pojo.Permission;
import com.adk.admin.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;


    @Override
    public Admin findAdminByUserName(String username) {
        LambdaQueryWrapper<Admin> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        queryWrapper.last("limit 1");
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public List<Permission> findPermissionByAdminId(Long id) {
        return adminMapper.findPermissionByAdminId(id);
    }
}
