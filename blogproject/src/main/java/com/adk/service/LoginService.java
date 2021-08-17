package com.adk.service;

import com.adk.vo.Result;
import com.adk.vo.params.LoginParams;

public interface LoginService {
    Result login(LoginParams loginParams);
}
