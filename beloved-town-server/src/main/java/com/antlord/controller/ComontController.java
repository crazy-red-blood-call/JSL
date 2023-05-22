package com.antlord.controller;

import com.antlord.bean.Result;
import com.antlord.dto.params.UploadParam;
import com.antlord.service.ComontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共接口
 */
@RestController
@RequestMapping("/comont")
public class ComontController {

    @Autowired
    private ComontService comontService;

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public Result upload(UploadParam param) throws Exception {
        return comontService.upload(param);
    }
}
