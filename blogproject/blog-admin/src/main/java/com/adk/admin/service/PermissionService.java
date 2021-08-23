package com.adk.admin.service;

import com.adk.admin.dao.pojo.Permission;
import com.adk.admin.model.PageParam;
import com.adk.admin.vo.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface PermissionService {
    Result listPermission(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
