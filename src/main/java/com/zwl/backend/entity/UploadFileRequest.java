package com.zwl.backend.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zwl
 * @date 2021/1/6 20:29
 * @describe instructions...
 */
@Data
public class UploadFileRequest {
    private String username;

    //上传文件会自动绑定到该属性
    private MultipartFile file1;
}
