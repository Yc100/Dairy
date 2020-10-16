/**
 * Copyright (c) 2020
 */

package com.yc.service.security;

import com.yc.common.entity.sys.SysUserEntity;
import com.yc.common.service.BaseService;


/**
 * 系统用户
 * 
 * @author YC
 */
public interface SysUserService extends BaseService<SysUserEntity> {

	SysUserEntity getByUsername(String username);

}