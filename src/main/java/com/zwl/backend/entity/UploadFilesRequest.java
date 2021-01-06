package com.zwl.backend.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zwl
 * @date 2021/1/6 20:29
 * @describe instructions...
 */
@Data
public class UploadFilesRequest {
    private String username;

    //上传文件会自动绑定到该属性
    private List<MultipartFile> file;
}
