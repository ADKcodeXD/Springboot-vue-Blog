package com.adk.admin.service;

import com.adk.admin.dao.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private AdminService adminService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin=adminService.findAdminByUserName(username);
        if (admin==null){
            return null;
        }
        UserDetails user = new User(username,admin.getPassword(),new ArrayList<>());
        return user;
    }
}
