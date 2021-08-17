package com.adk.service;

import com.adk.pojo.SysUser;

public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);
}
