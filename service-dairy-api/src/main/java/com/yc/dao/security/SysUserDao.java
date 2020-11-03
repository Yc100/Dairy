/**
 * Copyright (c) 2020
 */

package com.yc.dao.security;

import com.yc.common.dao.BaseDao;
import com.yc.common.entity.sys.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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

	List<SysUserEntity> getListPage(Map<String,Object> params);

	List<SysUserEntity> myFocusListPage(Map<String,Object> params);


}