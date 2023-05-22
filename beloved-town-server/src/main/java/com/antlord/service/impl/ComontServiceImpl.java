package com.antlord.service.impl;

import com.antlord.bean.Result;
import com.antlord.dto.params.UploadParam;
import com.antlord.enums.RsultCodeEnum;
import com.antlord.service.ComontService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class ComontServiceImpl implements ComontService {

    @Value("${file.local}")
    private String filePath;

    @Value("${file.run}")
    private String runPath;

    @Override
    public Result upload(UploadParam param) {
        if (param.getFile().isEmpty()) {
            return Result.failed(RsultCodeEnum.FAILED);
        }
        String fileExt = param.getFile().getOriginalFilename().substring(param.getFile().getOriginalFilename().lastIndexOf(".") + 1);
        File upload = new File(filePath.substring(filePath.indexOf(":") + 1));
        if(!upload.exists()) {
            upload.mkdirs();
        }
        String fileName = System.currentTimeMillis() + "." + fileExt;
        File dest = new File(upload.getAbsolutePath() + "/" + fileName);
        try {
            param.getFile().transferTo(dest);
            return Result.success("http://localhost:8081/" + runPath.substring(1, runPath.lastIndexOf("/")) + "/" + fileName);
        } catch (IOException e) {
            log.error("===>error:{}", e.getMessage(), e);
            return Result.failed();
        }
    }
}
