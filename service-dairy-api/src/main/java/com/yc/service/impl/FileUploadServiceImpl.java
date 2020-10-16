/**
 * Copyright (c) 2020
 */

package com.yc.service.impl;

import com.yc.common.exception.ApplicationException;
import com.yc.common.utils.DateUtils;
import com.yc.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * 文件上传 业务逻辑处理实现类
 *
 * @author YC
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Value("${ec.uploadPath}")
    String uploadPath;

    /**
     * @Description: 文件上传
     * @author YC
     * @date 2019-4-10 10:06
     */
    @Override
    public String fileUpload(MultipartFile upfile) {
        if (upfile.isEmpty()) {
            throw new ApplicationException("上传文件为空");
        }
        // 获取文件名
        String originalFileName = upfile.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));
        // 这里使用随机字符串来重新命名图片
        // fileName = Calendar.getInstance().getTimeInMillis() + Randoms.getRandomStringLower(4) + suffixName;
        String fileName = Calendar.getInstance().getTimeInMillis() + suffixName;
        // 通过静态资源映射 d:/upload/ 会映射到http://项目地址/files 路径下
        // 静态资源映射见bqinfo.project.common.config.WebConfigurer.java文件
        // File dest = new File(ConstantL.IMAGE_UPDATE_PATH + fileName);
        File dest = new File(uploadPath + "file/" + DateUtils.getCurrentDateByFormat("yyyyMMdd") + "/" + fileName);
        dest.setWritable(true, false);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().setWritable(true, false);
            dest.getParentFile().mkdirs();
        }
        try {
            upfile.transferTo(dest);
            //url的值为图片的URL访问地址 因为使用了静态资源映射 /files/目录是映射到了d:/upload/ d:/upload/是WEB服务器以外的存放地址
            String config = "{\"state\": \"SUCCESS\"," +
                    // "\"url\": \"" + ConstantL.BASE_URL + fileName + "\"," +
                    "\"url\": \"" + "/files/file/" + DateUtils.getCurrentDateByFormat("yyyyMMdd") + "/" + fileName + "\"," +
                    "\"title\": \"" + fileName + "\"," +
                    "\"original\": \"" + originalFileName + "\"}";
            return "/files/file/" + DateUtils.getCurrentDateByFormat("yyyyMMdd") + "/" + fileName;
        } catch (IllegalStateException e) {

            logger.error("上传文件失败 错误信息：" + e.getMessage());
            throw new ApplicationException("上传文件失败");
        } catch (IOException e) {

            logger.error("上传文件失败 错误信息：" + e.getMessage());
            throw new ApplicationException("上传文件失败");
        }
    }

}
