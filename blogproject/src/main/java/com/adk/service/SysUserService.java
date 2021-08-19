package com.adk.service;

import com.adk.pojo.SysUser;
import com.adk.vo.Result;
import com.adk.vo.UserVo;

public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    void save(SysUser sysUser);

    SysUser findUserByAccount(String account);

    UserVo findUserVoById(Long authorId);
}
