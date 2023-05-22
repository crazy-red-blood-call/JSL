package com.antlord.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.antlord.bean.JwtUserBean;
import com.antlord.bean.Result;
import com.antlord.config.JwtConfig;
import com.antlord.constants.StringConstant;
import com.antlord.dto.LoginDTO;
import com.antlord.dto.params.RoleUserParam;
import com.antlord.dto.params.SysUserParam;
import com.antlord.dto.params.UpdateLockParam;
import com.antlord.entity.SysUser;
import com.antlord.enums.RsultCodeEnum;
import com.antlord.enums.YesNoEnum;
import com.antlord.mapper.SysUserMapper;
import com.antlord.service.SysUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public Result login(LoginDTO param) {
        if (StrUtil.isBlank(param.getAccountName())) {
            return Result.failed(RsultCodeEnum.ACCOUNT_NAME_BLANK);
        }
        if (StrUtil.isBlank(param.getAccountName())) {
            return Result.failed(RsultCodeEnum.PASSWORD_BLANK);
        }

        SysUser sysUserParam = new SysUser();
        sysUserParam.setAccountName(param.getAccountName());
        List<SysUser> userList = sysUserMapper.findPreciselyUserList(sysUserParam);
        if (CollectionUtil.isEmpty(userList)) {
            return Result.failed(RsultCodeEnum.ACCOUNT_NAME_NOT_EXIT);
        }

        SysUser sysUser = userList.get(0);
        if (!sysUser.getPassword().equals(param.getPassword())) {
            return Result.failed(RsultCodeEnum.PASSWORD_ERROR);
        }
        if (YesNoEnum.YES.getValue().equals(sysUser.getLockFlag())) {
            return Result.failed(RsultCodeEnum.ACCOUNT_LOCK);
        }

        List<String> roleNameList = sysUserMapper.getRoleNameList(sysUser.getUserId());
        sysUser.setRoleNameList(roleNameList);

        //  创建token
        JwtUserBean user = new JwtUserBean();
        user.setAccountName(sysUser.getAccountName());
        user.setUserName(sysUser.getUserName());
        user.setPhone(sysUser.getPhone());
        user.setUserId(sysUser.getUserId());
        user.setRoleNameList(roleNameList);

        String token = jwtConfig.createToken(user);
        user.setToken("Bearer " + token);
        return Result.success(user);
    }

    @Override
    public Result addUser(SysUser user) {
        List<SysUser> userList = sysUserMapper.findPreciselyUserList(user);
        if (CollectionUtil.isNotEmpty(userList) && !userList.get(0).getUserId().equals(user.getUserId())) {
            return Result.failed(RsultCodeEnum.USER_EXIST);
        }

        SysUser userInfo = JwtConfig.getUserInfo();

        // 编辑
        if (StrUtil.isNotBlank(user.getUserId())) {
            if (user.getUserId().equals("1")) {
                return Result.failed(RsultCodeEnum.ADMIN_NOT_EDIT);
            }
            sysUserMapper.updateUser(user, userInfo.getAccountName());
        } else {
            // 新增
            user.setUserId(IdUtil.simpleUUID());
            user.setPassword(SecureUtil.md5("123456"));
            sysUserMapper.saveUser(user, userInfo.getAccountName());
        }

        return Result.success();
    }

    @Override
    public Result getUserList(SysUserParam param) {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(param, sysUser);
        Page<SysUser> page = PageHelper.startPage(param.getPageNo(), param.getPageSize());
        List<SysUser> userList = sysUserMapper.findFuzzyUserList(sysUser);
        userList.forEach(item -> {
            item.setPassword(StringConstant.BLANK);
            if (StrUtil.isNotBlank(item.getRoleIds())) {
                String roleIds = item.getRoleIds();
                item.setRoleIdList(Arrays.asList(roleIds.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList()));
            }
        });
        PageInfo<SysUser> pageInfo = PageInfo.of(page);

        return Result.success(pageInfo);
    }

    @Override
    public Result deleteUser(List<String> param) {

        if (param.contains("1")) {
            return Result.failed(RsultCodeEnum.ADMIN_NOT_DELETE);
        }
        SysUser userInfo = JwtConfig.getUserInfo();
        sysUserMapper.deleteUser(param, userInfo.getAccountName());

        return Result.success();
    }

    @Override
    public Result updateLock(UpdateLockParam param) {
        SysUser userInfo = JwtConfig.getUserInfo();
        sysUserMapper.updateLock(param, userInfo.getAccountName());
        return Result.success();
    }

    @Override
    public Result resetPassword(String userId) {
        SysUser userInfo = JwtConfig.getUserInfo();
        String newPasswordStr = "Npass@#2023";
        String newPassword = SecureUtil.md5(newPasswordStr);
        sysUserMapper.updatePassword(userId, newPassword, userInfo.getAccountName());
        return Result.success(StrUtil.format("新密码为{}，请查收", newPasswordStr));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateRoleUser(RoleUserParam poleUserParam) {
        sysUserMapper.deleteAllRole(poleUserParam.getUserId());
        List<RoleUserParam> roleUserParamList = Lists.newArrayList();
        RoleUserParam roleUserParam;
        for (String roleId : poleUserParam.getRoleIdList()) {
            roleUserParam = new RoleUserParam();
            roleUserParam.setUserId(poleUserParam.getUserId());
            roleUserParam.setRoleId(roleId);
            roleUserParamList.add(roleUserParam);
        }
        if (CollectionUtil.isNotEmpty(roleUserParamList)) {
            sysUserMapper.addRoles(roleUserParamList);
        }
        return Result.success();
    }

}
