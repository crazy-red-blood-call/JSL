package com.antlord.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * Description: 系统角色实体类
 * @Author: CHENZHIWEI
 * Version: 1.0
 * Create Date Time: 2023-05-06 15:50:11
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemRole implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色代码
	 */
	// @ApiModelProperty(name = "code", value = "角色代码", dataType = "String")
	private String code;

	/**
	 * 创建者
	 */
	// @ApiModelProperty(name = "createBy", value = "创建者", dataType = "String")
	private String createBy;

	/**
	 * 创建时间
	 */
	// @ApiModelProperty(name = "createTime", value = "创建时间", dataType = "String")
	private String createTime;

	/**
	 * 删除标志[1-删除,0-未删除]
	 */
	// @ApiModelProperty(name = "deleteFlag", value = "删除标志[1-删除,0-未删除]", dataType = "Integer")
	private Integer deleteFlag;

	/**
	 * 角色全称
	 */
	// @ApiModelProperty(name = "fullName", value = "角色全称", dataType = "String")
	private String fullName;

	/**
	 * 自增id
	 */
	// @ApiModelProperty(name = "id", value = "自增id", dataType = "Long")
	private Long id;

	/**
	 * 角色标注，普通用户(0),管理员(1)
	 */
	// @ApiModelProperty(name = "manager", value = "角色标注，普通用户(0),管理员(1)", dataType = "Integer")
	private Integer manager;

	/**
	 * 角色名称
	 */
	// @ApiModelProperty(name = "name", value = "角色名称", dataType = "String")
	private String name;

	/**
	 * 备注
	 */
	// @ApiModelProperty(name = "remark", value = "备注", dataType = "String")
	private String remark;

	/**
	 * 排序顺序号
	 */
	// @ApiModelProperty(name = "sort", value = "排序顺序号", dataType = "Integer")
	private Integer sort;

	/**
	 * 角色状态（1-正常，2-待激活，0-已停用）
	 */
	// @ApiModelProperty(name = "status", value = "角色状态（1-正常，2-待激活，0-已停用）", dataType = "Integer")
	private Integer status;

	/**
	 * 更新者
	 */
	// @ApiModelProperty(name = "updateBy", value = "更新者", dataType = "String")
	private String updateBy;

	/**
	 * 更新时间
	 */
	// @ApiModelProperty(name = "updateTime", value = "更新时间", dataType = "String")
	private String updateTime;

	/**
	 * 页码
	 */
	private Integer pageNo;

	/**
	 * 记录数
	 */
	private Integer pageSize;

}