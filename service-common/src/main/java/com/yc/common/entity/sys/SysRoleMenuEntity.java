/**
 * Copyright (c) 2020
 */

package com.yc.common.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色菜单关系
 *
 * @author YC
 * @createDate 2020/10/16 14:33
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_role_menu")
public class SysRoleMenuEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 菜单ID
	 */
	private Long menuId;

}
