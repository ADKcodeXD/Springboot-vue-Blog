package com.adk.service.Impl;

import com.adk.pojo.SysUser;
import com.adk.service.LoginService;
import com.adk.service.SysUserService;
import com.adk.utils.JWTUtils;
import com.adk.vo.ErrorCode;
import com.adk.vo.Result;
import com.adk.vo.params.LoginParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
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

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        //使用jwtutils来进行token的检验，若不合法则返回空值
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map==null){
            return null;
        }
        //去redis中获取userJson 若为空则再次返回(说明过期了)
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(StringUtils.isBlank(userJson)){
            return null;
        }
        //用阿里巴巴的fastjson将字符串解析成对象
        SysUser sysUser = JSON.parseObject(userJson,SysUser.class);
        return sysUser;
    }


    /**
     * 退出登录功能，逻辑删除redis中的token值
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }


    /**
     * 注册的业务逻辑实现，后期可以分离出来
     * @param loginParams
     * @return
     */
    @Override
    public Result register(LoginParams loginParams) {
        /**
         * 判断合法性
         * 判断账户存在否
         * 开启事务     并添加账户
         * 生成token 传入redis 返回页面
         */
        String account=loginParams.getAccount();
        String password=loginParams.getPassword();
        String nickname=loginParams.getNickname();
        if (StringUtils.isBlank(account)||StringUtils.isBlank(password)||StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        SysUser sysUser=sysUserService.findUserByAccount(account);
        if(sysUser!=null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser=new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);

        //通过jwt加密用户类 并获取token
        String token = JWTUtils.createToken(sysUser.getId());
        //通过redis将用户类存放到内存中
        //第一个参数为token  第二个参数为用户信息 第三个参数为过期时效 第四个参数为过期时效单位
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);

        return Result.success(token);
    }


    public static void main(String[] args) {
        String password="123456";
        password= DigestUtils.md5Hex(password+slat);
        System.out.println(password);
    }
}
