/*
* Copyright (c) 2017 citycloud.com.cn All Rights Reserved.
*/
package com.yc.auth;

import com.yc.common.constant.Constant;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author YC
 * @createDate 2020/8/12 11:44
 **/
public class HeaderParameterBuilder {

    public HeaderParameterBuilder(){
    }

    public List<Parameter> build(){
        List<Parameter> parameterList = new ArrayList<Parameter>();
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name(Constant.REQUEST_HEADERS_AUTH_KEY).description("用户认证字段").hidden(false).modelRef(new
                ModelRef("string"))
                .parameterType("header").required(false);
        parameterList.add(parameterBuilder.build());
        return parameterList;
    }
}
