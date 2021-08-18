package com.adk.service.Impl;


import com.adk.dao.mapper.SysUserMapper;
import com.adk.pojo.SysUser;
import com.adk.service.LoginService;
import com.adk.service.SysUserService;
import com.adk.vo.ErrorCode;
import com.adk.vo.LoginUserVo;
import com.adk.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser=sysUserMapper.selectById(id);
        if (sysUser==null){
            sysUser = new SysUser();
            sysUser.setNickname("默认作者");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getNickname,SysUser::getAvatar,SysUser::getAccount);
        queryWrapper.last("limit 1");//加上limit1加快查询效率
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 根据token来搜索用户的信息
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.token合法性的校验
         * 2.redis是否存在
         * 3.成功则返回LoginUserVo
         */
        SysUser sysUser = loginService.checkToken(token);

        if (sysUser==null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        //如果为空则直接返回失败的结果
        LoginUserVo loginUserVo=new LoginUserVo();

        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());

        return Result.success(loginUserVo);
    }


    /**
     * 保存用户的操作
     * @param sysUser
     */
    @Override
    public void save(SysUser sysUser) {
        //id会自动生成
        //mybatisplus提供了一个默认生成id的函数  使用雪花算法来进行生成
        sysUserMapper.insert(sysUser);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return this.sysUserMapper.selectOne(queryWrapper);
    }
}
