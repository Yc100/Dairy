package com.yc.dao.others;

import com.yc.common.dao.BaseDao;
import com.yc.common.entity.others.OptionClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 业务参数管理 Mapper 接口
 * @author YC
 * @date 2019-2-27 21:53
 */
@Mapper
public interface OptionDao extends BaseDao<OptionClass> {

    /**
     * 获取参数所有列表
     * @return 返回结果
     */
    List<OptionClass> queryOptionList(String typeId);

    /**
     * 根据参数大类ID和CODE查询业务参数详情
     * @return 返回结果
     */
    OptionClass getOptionByCode(@Param("typeId") String typeId, @Param("dataCode") String dataCode);

    /**
     * 根据参数大类ID和参数title查询业务参数详情
     * @return 返回结果
     */
    OptionClass getOptionByDataTitle(@Param("typeId") String typeId, @Param("dataTitle") String dataTitle);
}
