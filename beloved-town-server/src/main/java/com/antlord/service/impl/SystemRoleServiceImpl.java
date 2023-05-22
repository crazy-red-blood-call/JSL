package com.antlord.service.impl;

import com.antlord.service.SystemRoleService;
import com.antlord.mapper.SystemRoleMapper;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import com.antlord.bean.Result;
import com.antlord.entity.SystemRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Description: 系统角色服务实现类
 * @Author: CHENZHIWEI
 * Version: 1.0
 * Create Date Time: 2023-05-06 15:50:11
 *
 */
@Service
@Slf4j
public class SystemRoleServiceImpl implements SystemRoleService {
	/**
	 * 	系统角色数据库处理类
	 */
	@Autowired
	private SystemRoleMapper systemRoleMapper;

	@Override
	public Result addSystemRole(SystemRole systemRole){
		systemRoleMapper.addSystemRole(systemRole);
		return Result.success();
	}

	@Override
	public Result getSystemRoleList(SystemRole systemRole){
		Page<SystemRole> page = PageHelper.startPage(systemRole.getPageNo(), systemRole.getPageSize());
		systemRoleMapper.getSystemRoleList(systemRole);
		PageInfo<SystemRole> pageInfo = PageInfo.of(page);
		return Result.success(pageInfo);
	}

	@Override
	public Result updateSystemRole(SystemRole systemRole){
		systemRoleMapper.updateSystemRole(systemRole);
		return Result.success();
	}

	@Override
	public Result deleteSystemRole(List<String> id){
		systemRoleMapper.deleteSystemRole(id);
		return Result.success();
	}
}