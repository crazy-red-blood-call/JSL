package com.antlord.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Description: 美甲实体类
 * @Author: author
 * Version: 1.0
 * Create Date Time: 2023-05-13 20:23:25
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Manicure implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 描述
	 */
	// @ApiModelProperty(name = "content", value = "描述", dataType = "String")
	private String content;

	/**
	 * 创建人
	 */
	// @ApiModelProperty(name = "createBy", value = "创建人", dataType = "String")
	private String createBy;

	/**
	 * 创建时间
	 */
	// @ApiModelProperty(name = "createTime", value = "创建时间", dataType = "String")
	private String createTime;

	/**
	 * 删除标志[1-已删除,0-正常]
	 */
	// @ApiModelProperty(name = "deleteFlag", value = "删除标志[1-已删除,0-正常]", dataType = "Integer")
	private Integer deleteFlag;

	/**
	 * 主键id
	 */
	// @ApiModelProperty(name = "id", value = "主键id", dataType = "Long")
	private Long id;

	/**
	 * 图片地址
	 */
	// @ApiModelProperty(name = "imgs", value = "图片地址", dataType = "String")
	private String imgs;

	/**
	 * 启用/停用
	 */
	// @ApiModelProperty(name = "status", value = "启用/停用", dataType = "String")
	private String status;

	/**
	 * 标题
	 */
	// @ApiModelProperty(name = "title", value = "标题", dataType = "String")
	private String title;

	/**
	 * 操作人
	 */
	// @ApiModelProperty(name = "updateBy", value = "操作人", dataType = "String")
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

	private String isKh;

}