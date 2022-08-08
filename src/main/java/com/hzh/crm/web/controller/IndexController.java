package com.hzh.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author DAHUANG
 * @date 2022/3/9
 */

@Controller
public class IndexController {


    /**
     * url：http://127.0.0.1:8080/crm ,在spring中被简化为 /,并且只能省去
     * @return
     */
    @RequestMapping("/")
    public String index(){
        //请求转发
        return "index";
    }
    

}
