package com.antlord.service;
import com.antlord.bean.Result;
import com.antlord.entity.SystemRole;

import java.util.List;

/**
 * Description: 系统角色服务类
 * @Author: CHENZHIWEI
 * Version: 1.0
 * Create Date Time: 2023-05-06 15:50:11
 *
 */
public interface SystemRoleService {

    Result addSystemRole(SystemRole systemRole);

    Result getSystemRoleList(SystemRole systemRole);

    Result updateSystemRole(SystemRole systemRole);

    Result deleteSystemRole(List<String> id);

}