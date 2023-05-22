package com.antlord.controller;

import com.antlord.bean.Result;
import com.antlord.entity.Manicure;
import com.antlord.service.ManicureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: 美甲接口
 * @Author: author
 * Version: 1.0
 * Create Date Time: 2023-05-13 20:23:25
 *
 */
@RestController
@RequestMapping("/manicure")
@Slf4j
public class ManicureController {

	/**
	 * 	美甲服务类
	 */
	@Autowired
	private ManicureService manicureService;

	/**
	 * 新增美甲
	 */
	@PostMapping("/addManicure")
	public Result addManicure(@RequestBody Manicure manicure) {
		return manicureService.addManicure(manicure);
	}

	/**
	 * 查询美甲列表
	 */
	@PostMapping("/getManicureList")
	public Result getManicureList(@RequestBody Manicure manicure) {
		return manicureService.getManicureList(manicure);
	}

	/**
	 * 更新美甲
	 */
	@PostMapping("/updateManicure")
	public Result updateManicure(@RequestBody Manicure manicure) {
		return manicureService.updateManicure(manicure);
	}

	/**
	 * 删除美甲
	 */
	@PostMapping("/deleteManicure")
	public Result deleteManicure(@RequestBody List<String> idList) {
		return manicureService.deleteManicure(idList);
	}

}