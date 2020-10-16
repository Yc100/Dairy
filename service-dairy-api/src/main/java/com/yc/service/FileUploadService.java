/**
 * Copyright (c) 2020
 */

package com.yc.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 文件上传 业务逻辑处理接口
 *
 * @author YC
 */
public interface FileUploadService{

    /**
     * @Description: 文件上传
     * @author YC
     * @date 2019-4-10 10:06
     */
    String fileUpload(MultipartFile upfile);


}

