/**
 * Copyright (c) 2020
 */

package com.yc.common.entity.sys;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 菜单管理
 *
 * @author YC
 * @createDate 2020/10/16 14:32
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_menu")
public class SysMenuEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	private Long pid;
	/**
	 * 菜单名称
	 */
	@TableField(exist = false)
	private String name;
	/**
	 * 菜单URL
	 */
	private String url;
	/**
	 * 授权(多个用逗号分隔，如：sys:user:list,sys:user:save)
	 */
	private String permissions;
	/**
	 * 类型   0：菜单   1：按钮
	 */
	private Integer type;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 更新者
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updater;
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
	/**
	 * 上级菜单名称
	 */
	@TableField(exist = false)
	private String parentName;

}