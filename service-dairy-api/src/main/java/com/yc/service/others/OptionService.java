package com.yc.service.others;

import com.yc.common.entity.others.OptionClass;
import com.yc.common.service.BaseService;

import java.util.List;

/**
 * @Description: 业务参数查询 业务逻辑处理接口
 * @author YC
 * @date 2019-2-14 17:11
 */
public interface OptionService extends BaseService<OptionClass> {

    /**
     * @Description: 获取参数所有列表
     * @author YC
     * @date 2019-4-24 13:48
     */
    List<OptionClass> queryOptionList(String typeId);

    /**
     * @Description: 根据参数大类ID和CODE查询业务参数详情
     * @author YC
     * @date 2019-4-24 13:48
     */
    OptionClass getOptionByCode(String typeId, String dataCode);

    /**
     * @Description: 根据参数大类ID和参数title查询业务参数详情
     * @author YC
     * @date 2019-4-24 13:48
     */
    OptionClass getOptionByDataTitle(String typeId, String dataTitle);
}
