package com.zwl.backend.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zwl
 * @date 2021/1/6 21:20
 * @describe 文件下载...
 */
@Controller
public class DownLoadController {

    @RequestMapping(value = "/download")
    public void downloadFile(String fileName,  HttpServletResponse response){
        if(fileName != null){
            String realPath = "E:\\Java\\download";
            File file = new File(realPath, fileName);
            OutputStream out = null;
            if(file.exists()){
                //设置下载完毕不打开文件
                response.setContentType("application/force-download");
                //编码防止中文文件名出现乱码
                fileName= UriEncoder.encode(fileName);
                response.setHeader("Content-Disposition", "attachment;filename="+fileName);
                try {
                    out = response.getOutputStream();
                    //使用工具类
                    out.write(FileUtils.readFileToByteArray(file));
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    if(out != null){
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }



    @RequestMapping(value="/download2",method= RequestMethod.GET) //匹配的是href中的download请求
    public ResponseEntity<byte[]> download2( String fileName) throws IOException {

        String realPath = "E:\\Java\\download";
        File file = new File(realPath, fileName);

        HttpHeaders headers = new HttpHeaders();//http头信息

        //编码防止中文文件名出现乱码
        fileName= UriEncoder.encode(fileName);

        headers.setContentDispositionFormData("attachment", fileName);

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);

    }
}
