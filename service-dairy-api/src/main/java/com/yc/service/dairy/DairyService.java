/**
 * Copyright (c) 2020
 */

package com.yc.service.dairy;

import com.yc.common.entity.dairy.DairyEntity;
import com.yc.common.entity.dairy.DairyReadEntity;
import com.yc.common.entity.sys.SysUserEntity;
import com.yc.common.page.PageData;
import com.yc.common.service.BaseService;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;


/**
 * Description
 *
 * @author YC
 * @createDate 2020/10/21 14:24
 **/
public interface DairyService extends BaseService<DairyEntity> {
	PageData<DairyEntity> getListPage(Map<String, Object> params);

	BigDecimal getReadCountByDairyId(Long dairyId);

	Long getReadCount(@Param("dairyId") Long dairyId, @Param("userId") Long userId);

	void saveDairyRead(DairyReadEntity dairyReadEntity);

	void dairyReadCountAdd(DairyReadEntity dairyReadEntity);

}