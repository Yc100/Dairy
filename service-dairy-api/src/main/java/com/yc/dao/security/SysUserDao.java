/**
 * Copyright (c) 2020
 */

package com.yc.dao.security;

import com.yc.common.dao.BaseDao;
import com.yc.common.entity.sys.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户
 * 
 * @author YC
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {

	SysUserEntity getByUsername(String username);

	/**
	 * 获取用户信息
	 *
	 * @param id 用户编号
	 */
	SysUserEntity getUserInfo(long id);

}