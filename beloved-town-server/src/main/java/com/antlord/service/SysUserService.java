package com.antlord.service;

import com.antlord.bean.Result;
import com.antlord.dto.LoginDTO;
import com.antlord.dto.params.RoleUserParam;
import com.antlord.dto.params.SysUserParam;
import com.antlord.dto.params.UpdateLockParam;
import com.antlord.entity.SysUser;

import java.util.List;

public interface SysUserService {

    Result login(LoginDTO param);

    Result addUser(SysUser user);

    Result getUserList(SysUserParam param);

    Result deleteUser(List<String> param);

    Result updateLock(UpdateLockParam param);

    Result resetPassword(String userId);

    Result updateRoleUser(RoleUserParam poleUserParam);
}
