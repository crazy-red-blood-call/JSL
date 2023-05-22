package com.antlord.controller;

import com.antlord.bean.Result;
import com.antlord.entity.SystemRole;
import com.antlord.service.SystemRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: 系统角色接口
 * @Author: CHENZHIWEI
 * Version: 1.0
 * Create Date Time: 2023-05-06 15:50:11
 *
 */
@RestController
@RequestMapping("/systemRole")
@Slf4j
public class SystemRoleController {

	/**
	 * 	系统角色服务类
	 */
	@Autowired
	private SystemRoleService systemRoleService;

	/**
	 * 新增系统角色
	 */
	@PostMapping("/addSystemRole")
	public Result addSystemRole(@RequestBody SystemRole systemRole) {
		return systemRoleService.addSystemRole(systemRole);
	}

	/**
	 * 查询系统角色列表
	 */
	@PostMapping("/getSystemRoleList")
	public Result getSystemRoleList(@RequestBody SystemRole systemRole) {
		return systemRoleService.getSystemRoleList(systemRole);
	}

	/**
	 * 更新系统角色
	 */
	@PostMapping("/updateSystemRole")
	public Result updateSystemRole(@RequestBody SystemRole systemRole) {
		return systemRoleService.updateSystemRole(systemRole);
	}

	/**
	 * 删除系统角色
	 */
	@PostMapping("/deleteSystemRole")
	public Result deleteSystemRole(@RequestBody List<String> id) {
		return systemRoleService.deleteSystemRole(id);
	}

}