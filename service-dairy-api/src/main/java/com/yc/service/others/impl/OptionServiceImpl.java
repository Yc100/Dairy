package com.yc.service.others.impl;

import com.yc.common.entity.others.OptionClass;
import com.yc.common.service.impl.BaseServiceImpl;
import com.yc.dao.others.OptionDao;
import com.yc.service.others.OptionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务参数查询  服务实现类
 * @author YC
 * @date 2019-3-14 17:11
 */
@Service
public class OptionServiceImpl extends BaseServiceImpl<OptionDao, OptionClass> implements OptionService {


    @Override
    public List<OptionClass> queryOptionList(String typeId) {
        return baseDao.queryOptionList(typeId);
    }

    @Override
    public OptionClass getOptionByCode(String typeId, String dataCode){
        return baseDao.getOptionByCode(typeId, dataCode);
    }

    @Override
    public OptionClass getOptionByDataTitle(String typeId, String dataTitle){
        return baseDao.getOptionByDataTitle(typeId, dataTitle);
    }
}
