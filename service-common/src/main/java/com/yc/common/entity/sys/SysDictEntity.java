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
 * 数据字典
 *
 * @author YC
 * @param
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_dict")
public class SysDictEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 上级ID，一级为0
	 */
	private Long pid;
	/**
	 * 字典类型
	 */
	private String dictType;
	/**
	 * 字典名称
	 */
	private String dictName;
	/**
	 * 字典值
	 */
	private String dictValue;
	/**
	 * 备注
	 */
	private String remark;
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
}