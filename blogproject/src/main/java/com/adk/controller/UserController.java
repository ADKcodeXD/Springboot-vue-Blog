package com.adk.controller;

import com.adk.service.SysUserService;
import com.adk.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private SysUserService sysUserService;


    /**
     * 通过RequestHeader来接受请求头信息中的token
     */
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization")String token){
        return sysUserService.findUserByToken(token);
    }

    @GetMapping("currentUserAllInfo/{id}")
    public Result currentUserAllInfo(@PathVariable("id")String id){
        return sysUserService.findUserAllInfoById(id);
    }
}
