package com.zwl.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zwl
 * @date 2021/1/6 13:23
 * @describe instructions...
 */
@Controller
public class TestController {

    /***
     *
     * @Author zwl
     * @Date 2021/1/6
     * @url http://localhost:8080/test
     * @return java.lang.String
     **/
    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "hello world";
    }

    /***
     *
     * @Author zwl
     * @Date 2021/1/6
     * @url http://localhost:8080/index
     * @return java.lang.String
     **/
    @RequestMapping("/index")
    public String test2(){
        return "index";
    }
}
