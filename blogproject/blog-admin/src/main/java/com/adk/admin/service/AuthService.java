package com.adk.admin.service;

import com.adk.admin.dao.pojo.Admin;
import com.adk.admin.dao.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service(value = "authService")
public class AuthService  {

    @Autowired
    private AdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication){
        String requestURI = request.getRequestURI();
        Object principal = authentication.getPrincipal();

        if(principal==null||"anonymousUser".equals(principal)){
            return false;//未登录
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUserName(username);
        if(admin==null){
            return false;
        }
        if(1==admin.getId()){
            return true;
        }
        Long id=admin.getId();
        List<Permission> permissionList=adminService.findPermissionByAdminId(id);
        requestURI= StringUtils.split(requestURI,'?')[0];
        for (Permission permission : permissionList) {
            if(requestURI.equals(permission.getPath())){
                return true;
            }
        }
        return true;
    }
}
