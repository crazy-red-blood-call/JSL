package com.antlord.mapper;

import com.antlord.entity.SystemRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description: 系统角色数据库处理类
 * @Author: CHENZHIWEI
 * Version: 1.0
 * Create Date Time: 2023-05-06 15:50:11
 *
 */
@Mapper
public interface SystemRoleMapper {

    /**
     * 新增系统角色
     */
    void addSystemRole(SystemRole systemRole);

    /**
     * 查询系统角色
     */
    List<SystemRole> getSystemRoleList(SystemRole systemRole);

    /**
     * 更新系统角色
     */
    void updateSystemRole(SystemRole systemRole);

    /**
     * 删除系统角色
     */
    void deleteSystemRole(@Param("idList") List<String> id);

}