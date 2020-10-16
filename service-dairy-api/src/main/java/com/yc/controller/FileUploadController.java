package com.yc.controller;

import com.yc.auth.Authorization;
import com.yc.common.exception.ApplicationException;
import com.yc.common.utils.Result;
import com.yc.service.FileUploadService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 文件上传
 * @author YC
 */
@RestController
@Authorization
@RequestMapping("/file")
@Api(tags = "文件上传")
public class FileUploadController {

	@Autowired
	FileUploadService fileUploadService;

	/**
	 * @Description: 文件上传处理
	 * @author YC
	 * @date 2019-1-3 17:03
	 */
	@ApiOperation("文件上传服务")
	@PostMapping(value = "/upload")
	public Result fileUpload(@RequestParam("file") MultipartFile upfile) {
		if (upfile.isEmpty()) {
			throw new ApplicationException("文件为空，上传失败");
		}

		String fileUrl = fileUploadService.fileUpload(upfile);
		return new Result<String>().message(fileUrl);
	}
}
