package com.yc.dao.others;

import com.yc.common.dao.BaseDao;
import com.yc.common.entity.others.SmsMt;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信发送信息表
 *
 * @author YC YC@aiyangniu.cn
 * @since 1.0.0 2019-06-15
 */
@Mapper
public interface SmsMtDao extends BaseDao<SmsMt> {
	
}