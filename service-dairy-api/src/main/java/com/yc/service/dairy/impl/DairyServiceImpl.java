/**
 * Copyright (c) 2020
 */

package com.yc.service.dairy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yc.common.entity.dairy.DairyEntity;
import com.yc.common.entity.sys.SysUserEntity;
import com.yc.common.page.PageData;
import com.yc.common.service.impl.BaseServiceImpl;
import com.yc.dao.dairy.DairyDao;
import com.yc.dao.security.SysUserDao;
import com.yc.service.dairy.DairyService;
import com.yc.service.security.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author YC
 * @createDate 2020/10/21 14:24
 **/
@Service
public class DairyServiceImpl extends BaseServiceImpl<DairyDao, DairyEntity> implements DairyService {

    @Autowired
    private DairyDao dairyDao;


    @Override
    public PageData<DairyEntity> getListPage(Map<String, Object> params) {

        //分页
        IPage page = getPage(params, "create_date", false);

        List<DairyEntity> dairyEntities = baseDao.getListPage(params);

        return getPageData(dairyEntities, page.getTotal(), DairyEntity.class);
    }

}
