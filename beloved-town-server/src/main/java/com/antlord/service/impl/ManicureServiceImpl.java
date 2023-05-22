package com.antlord.service.impl;

import cn.hutool.core.util.StrUtil;
import com.antlord.bean.Result;
import com.antlord.config.JwtConfig;
import com.antlord.entity.Manicure;
import com.antlord.entity.SysUser;
import com.antlord.mapper.ManicureMapper;
import com.antlord.service.ManicureService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 美甲服务实现类
 * @Author: author
 * Version: 1.0
 * Create Date Time: 2023-05-13 20:23:25
 *
 */
@Service
@Slf4j
public class ManicureServiceImpl implements ManicureService {
	/**
	 * 	美甲数据库处理类
	 */
	@Autowired
	private ManicureMapper manicureMapper;

	@Override
	public Result addManicure(Manicure manicure){
		SysUser userInfo = JwtConfig.getUserInfo();
		manicure.setCreateBy(userInfo.getUserId());
		manicureMapper.addManicure(manicure);
		return Result.success();
	}

	@Override
	public Result getManicureList(Manicure manicure){
		Page<Manicure> page = PageHelper.startPage(manicure.getPageNo(), manicure.getPageSize());
		if (StrUtil.isBlank(manicure.getIsKh())) {
			SysUser userInfo = JwtConfig.getUserInfo();
			manicure.setCreateBy(userInfo.getUserId());
		}
		manicureMapper.getManicureList(manicure);
		PageInfo<Manicure> pageInfo = PageInfo.of(page);
		return Result.success(pageInfo);
	}

	@Override
	public Result updateManicure(Manicure manicure){
		manicureMapper.updateManicure(manicure);
		return Result.success();
	}

	@Override
	public Result deleteManicure(List<String> idList){
		manicureMapper.deleteManicure(idList);
		return Result.success();
	}
}