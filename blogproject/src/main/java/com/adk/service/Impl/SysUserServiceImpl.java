package com.adk.service.Impl;


import com.adk.dao.mapper.SysUserMapper;
import com.adk.pojo.SysUser;
import com.adk.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
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
}
