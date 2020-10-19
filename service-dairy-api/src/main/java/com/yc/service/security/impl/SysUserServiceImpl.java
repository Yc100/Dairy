/**
 * Copyright (c) 2020
 */

package com.yc.service.security.impl;

import com.yc.common.entity.sys.SysUserEntity;
import com.yc.common.service.impl.BaseServiceImpl;
import com.yc.dao.security.SysUserDao;
import com.yc.service.security.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统用户
 *
 * @author YC
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserDao userDao;

    @Override
    public SysUserEntity getByUsername(String username) {
        return baseDao.selectOne(new QueryWrapper<SysUserEntity>().lambda().eq(SysUserEntity::getUsername, username).last("limit 1"));
    }

    /**
     * 获取用户信息
     *
     * @param id 用户编号
     */
    public SysUserEntity getUserInfo(long id) {
        return userDao.getUserInfo(id);
    }

}
