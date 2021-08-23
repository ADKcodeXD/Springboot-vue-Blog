package com.adk.admin.service.Impl;

import com.adk.admin.dao.mapper.PermissionMapper;
import com.adk.admin.dao.pojo.Permission;
import com.adk.admin.model.PageParam;
import com.adk.admin.service.PermissionService;
import com.adk.admin.vo.PageResult;
import com.adk.admin.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public Result listPermission(PageParam pageParam) {
        Page<Permission> page=new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getQueryString())){
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, queryWrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }

    @Override
    public Result add(Permission permission) {
        permissionMapper.insert(permission);
        return Result.success(null);
    }

    @Override
    public Result update(Permission permission) {
        permissionMapper.updateById(permission);
        return Result.success(null);
    }

    @Override
    public Result delete(Long id) {
        permissionMapper.deleteById(id);
        return Result.success(null);
    }
}
