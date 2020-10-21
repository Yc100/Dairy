/**
 * Copyright (c) 2020
 */

package com.yc.dao.dairy;

import com.yc.common.dao.BaseDao;
import com.yc.common.entity.dairy.DairyEntity;
import com.yc.common.entity.sys.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author YC
 * @createDate 2020/10/21 14:22
 **/
@Mapper
public interface DairyDao extends BaseDao<DairyEntity> {

	List<DairyEntity> getListPage(Map<String, Object> params);

}