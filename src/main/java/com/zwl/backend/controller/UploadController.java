package com.zwl.backend.controller;

import com.zwl.backend.entity.UploadFileRequest;
import com.zwl.backend.entity.UploadFilesRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;


/**
 * @author zwl
 * @date 2021/1/6 14:22
 * @describe mvc文件上传...
 */
@Controller
public class UploadController {

    /**
     * 实现单文件的上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload",method=RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file1") MultipartFile file,@RequestParam("username")String username, HttpServletRequest request) {
        System.out.println(username+"文件上传开始...");

        if (!file.isEmpty()) {
            //项目上下文
            String contextPath = request.getContextPath();
            //访问地址 即/upload
            String servletPath = request.getServletPath();
            //"http"
            String scheme = request.getScheme();

            //存放我们上传的文件路径
            String storePath = "E:\\Java\\upload";

            //上传的文件名
            String fileName = file.getOriginalFilename();

            File filepath = new File(storePath, fileName);

            if (!filepath.getParentFile().exists()) {
                //如果目录不存在，创建目录
                filepath.getParentFile().mkdirs();
            }

            try {
                //方式一：使用file提供的方法，把文件写入目标文件地址
                /*file.transferTo(new File(storePath + File.separator + fileName));*/
                //方式二：将文件流拷贝到目标文件对象中
                FileUtils.copyInputStreamToFile(file.getInputStream(),
                        new File(storePath + File.separator + fileName));
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
            return "success";

        } else {
            return "error";
        }
    }

    /***
     * 多文件上传
     * @Author zwl
     * @Date 2021/1/6
     * @param multiRequest
     * @url http://localhost:8080/doUpload2
     * @return java.lang.String
     **/
    @RequestMapping(value="/doUpload2", method=RequestMethod.POST)
    @ResponseBody
    public String doUploadFile2(MultipartHttpServletRequest multiRequest) throws Exception {
        //获取普通表单属性
        String username=multiRequest.getParameter("username");
        System.out.println("username："+username);

        //获取普通表单属性
        Map<String, String[]> map=multiRequest.getParameterMap();
        for(String key:map.keySet()){
            System.out.println(key+"："+ multiRequest.getParameter(key));
            System.out.println(key+"："+ map.get(key)[0]);

        }

        //获取普通表单属性
        Enumeration<String> enumeration= multiRequest.getParameterNames();
        while (enumeration.hasMoreElements()){
            String value = (String)enumeration.nextElement();//调用nextElement方法获得元素
            System.out.print(value+":"+multiRequest.getParameter(value));

        }
        //遍历文件
        Iterator<String> filesNames = multiRequest.getFileNames();
        while(filesNames.hasNext()){
            String fileName =filesNames.next();
            MultipartFile file =  multiRequest.getFile(fileName);
            if(!file.isEmpty()){
                System.out.println("文件名："+file.getOriginalFilename());
                FileUtils.copyInputStreamToFile(file.getInputStream(),
                        new File("E:\\Java\\upload", System.currentTimeMillis()+ file.getOriginalFilename()));
            }
        }
        return "success";
    }


    @RequestMapping(value = "/doUpload3", method=RequestMethod.POST)
    @ResponseBody
    public String doUploadFile3(MultipartHttpServletRequest multiRequest) throws Exception{
        //另一种遍历方式，这时候要求前端文件表单的name值都为file
        List<MultipartFile> files = multiRequest.getFiles("file");
        String path = "E:\\Java\\upload";
        if(files != null && !files.isEmpty()){
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if(!file.isEmpty()){
                    System.out.println("文件名："+file.getOriginalFilename());
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, file.getOriginalFilename()));
                }
            }
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/doUpload4", method=RequestMethod.POST)
    @ResponseBody
    public String doUploadFile4(@RequestParam("file") MultipartFile[] files) throws Exception{
        //同样要求前端文件表单的name值都为file
        String path = "E:\\Java\\upload";
        for (MultipartFile file : files) {
            if (file.getSize() > 0) {
                String fileName = file.getOriginalFilename();
                System.out.println("文件名："+file.getOriginalFilename());

                File f = new File(path, fileName);
                file.transferTo(f);
            }
        }
        return "success";
    }



    /**http://localhost:8080/upload5
     * 实现单文件的上传
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload5",method=RequestMethod.POST)
    @ResponseBody
    public String upload5(@ModelAttribute UploadFileRequest request) {
        System.out.println(request.getUsername()+"文件上传开始...");
        MultipartFile file=request.getFile1();
        if (!file.isEmpty()) {
            //存放我们上传的文件路径
            String storePath = "E:\\Java\\upload";
            //上传的文件名
            String fileName = file.getOriginalFilename();
            File filepath = new File(storePath, fileName);
            if (!filepath.getParentFile().exists()) {
                //如果目录不存在，创建目录
                filepath.getParentFile().mkdirs();
            }
            try {
                //方式一：使用file提供的方法，把文件写入目标文件地址
                /*file.transferTo(new File(storePath + File.separator + fileName));*/
                //方式二：将文件流拷贝到目标文件对象中
                FileUtils.copyInputStreamToFile(file.getInputStream(),
                        new File(storePath + File.separator + fileName));
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
            return "success";
        } else {
            return "error";
        }
    }

    @RequestMapping(value = "/doUpload6", method=RequestMethod.POST)
    @ResponseBody
    public String doUploadFile6(@ModelAttribute UploadFilesRequest request) throws Exception{
        System.out.println(request.getUsername());
        List<MultipartFile> files=request.getFile();
        //同样要求前端文件表单的name值都为file
        String path = "E:\\Java\\upload";
        for (MultipartFile file : files) {
            if (file.getSize() > 0) {
                String fileName = file.getOriginalFilename();
                System.out.println("文件名："+file.getOriginalFilename());

                File f = new File(path, fileName);
                file.transferTo(f);
            }
        }
        return "success";
    }


}
