package com.antlord.dto.params;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class UploadParam implements Serializable {

    private MultipartFile file;
    private String id;
}
