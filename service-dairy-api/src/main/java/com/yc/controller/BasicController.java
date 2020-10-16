package com.yc.controller;

import com.yc.common.constant.Constant;
import com.yc.common.entity.sys.SysUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * 基础Controller
 *
 * @author YC
 */
@RestController
public class BasicController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpSession session;

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpSession getSession() {
        return session;
    }

    public SysUserEntity getUser() {
        SysUserEntity user = (SysUserEntity) session.getAttribute(Constant.SYSTEM_USER_KEYWORD);
        return user;
    }

    public Long getUserId(){
        return getUser().getId();
    }

    // 判断是否登录
    public Boolean isLogin() {
        SysUserEntity user = (SysUserEntity) session.getAttribute(Constant.SYSTEM_USER_KEYWORD);
        return null == user ? Boolean.FALSE : Boolean.TRUE;
    }

    // 保存客户端真实IP地址
    protected String getRemoteAddr() {

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    public ResponseEntity<FileSystemResource> export(File file, String fileName) {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + fileName + ".docx");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Headers", "Authentication");
        //headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }
}
