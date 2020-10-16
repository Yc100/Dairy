package com.yc.common.entity.others;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

    /**
     * 业务参数信息
     *
     * @author YC
     * @createDate 2020/10/16 14:31
     **/
    @Data
    @TableName(value = "option_class")
    public class OptionClass implements Serializable {

        private static final long serialVersionUID = 1L;

        @TableId(value = "id")
        private Integer id;

        private String typeId;

        private String typeName;

        private String dataCode;

        private String dataTitle;

        private String dataDetails;

        private int sortNum;

        private String remarks;

        private Integer deleted;
    }
