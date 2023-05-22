package com.antlord.controller;

import com.antlord.bean.Result;
import com.antlord.dto.LoginDTO;
import com.antlord.dto.params.RoleUserParam;
import com.antlord.dto.params.SysUserParam;
import com.antlord.dto.params.UpdateLockParam;
import com.antlord.entity.SysUser;
import com.antlord.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录接口
     * @param param
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody LoginDTO param) {
        return sysUserService.login(param);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("/addUser")
    @ResponseBody
    public Result addUser(@RequestBody SysUser user) {
        return sysUserService.addUser(user);
    }

    /**
     * 查询用户
     * @param param
     * @return
     */
    @PostMapping("/getUserList")
    @ResponseBody
    public Result getUserList(@RequestBody SysUserParam param) {
        return sysUserService.getUserList(param);
    }

    /**
     * 删除用户
     * @param param
     * @return
     */
    @PostMapping("/deleteUser")
    @ResponseBody
    public Result deleteUser(@RequestBody List<String> param) {
        return sysUserService.deleteUser(param);
    }

    /**
     * 锁定/解锁
     * @param param
     * @return
     */
    @PostMapping("/updateLock")
    @ResponseBody
    public Result updateLock(@RequestBody UpdateLockParam param) {
        return sysUserService.updateLock(param);
    }

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @PostMapping("/resetPassword")
    @ResponseBody
    public Result resetPassword(@RequestBody String userId) {
        return sysUserService.resetPassword(userId);
    }

    /**
     * 修改角色
     *
     * @param roleUserParam
     * @return
     */
    @PostMapping("/updateRoleUser")
    @ResponseBody
    public Result updateRoleUser(@RequestBody RoleUserParam roleUserParam) {
        return sysUserService.updateRoleUser(roleUserParam);
    }




}
