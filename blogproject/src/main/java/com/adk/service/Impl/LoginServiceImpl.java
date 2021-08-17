package com.adk.service.Impl;

import com.adk.pojo.SysUser;
import com.adk.service.LoginService;
import com.adk.service.SysUserService;
import com.adk.utils.JWTUtils;
import com.adk.vo.ErrorCode;
import com.adk.vo.Result;
import com.adk.vo.params.LoginParams;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //MD5加密的秘钥 固定在后面加上该字符串
    private static final String slat="adkBLOG#$%$@";

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1、检查参数合法性
         * 2、查询
         * 3、不存在即失败
         * 4、存在生成token
         * 5、返回给前端 存放入redis中
         */
        String account =loginParams.getAccount();
        String password =loginParams.getPassword();

        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        //加密数据
        password= DigestUtils.md5Hex(password+slat);


        SysUser user = sysUserService.findUser(account, password);
        if(user==null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //通过jwt加密用户类 并获取token
        String token = JWTUtils.createToken(user.getId());
        //通过redis将用户类存放到内存中
        //第一个参数为token  第二个参数为用户信息 第三个参数为过期时效 第四个参数为过期时效单位
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);

        return Result.success(token);
    }

    public static void main(String[] args) {
        String password="123456";
        password= DigestUtils.md5Hex(password+slat);
        System.out.println(password);
    }
}
