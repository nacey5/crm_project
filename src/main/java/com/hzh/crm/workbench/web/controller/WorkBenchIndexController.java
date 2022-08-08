package com.hzh.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author DAHUANG
 * @date 2022/3/11
 */
@Controller
public class WorkBenchIndexController {

    @RequestMapping("/workbench/index.do")
    public String index(){
        //跳转到业务主页面
        return "workbench/index";
    }
}
