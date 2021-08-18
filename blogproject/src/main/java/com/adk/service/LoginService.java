package com.adk.service;

import com.adk.pojo.SysUser;
import com.adk.vo.Result;
import com.adk.vo.params.LoginParams;

public interface LoginService {
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    Result logout(String token);

    Result register(LoginParams loginParams);
}
