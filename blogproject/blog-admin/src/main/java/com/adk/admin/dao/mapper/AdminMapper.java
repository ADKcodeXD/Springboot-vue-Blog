package com.adk.admin.dao.mapper;

import com.adk.admin.dao.pojo.Admin;
import com.adk.admin.dao.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("select * from ms_permission where id in (select permission_id from ms_admin_permission where admin_id =#{id})")
    List<Permission> findPermissionByAdminId(Long id);
}
