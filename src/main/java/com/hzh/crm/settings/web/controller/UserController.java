package com.hzh.crm.settings.web.controller;

import com.hzh.crm.commons.contans.Contants;
import com.hzh.crm.commons.domain.ReturnObject;
import com.hzh.crm.commons.utils.DateUtils;
import com.hzh.crm.settings.domain.User;
import com.hzh.crm.settings.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.SimpleFormatter;

/**
 * @author DAHUANG
 * @date 2022/3/9
 */
@Controller
public class UserController {

    @Resource
    private UserService userService;

    /**
     * url要和当前controller方法处理完请求信息之后，返回信息相应的资源目录保持一致
     * @return
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        //转发到登陆页面
        return "settings/qx/user/login";
    }


    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd",loginPwd);
        User user = userService.queryUserByLoginActAndPwd(map);
        ReturnObject returnObject=new ReturnObject();
        if (user==null){
            //登陆失败，用户名或密码错误
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        }else{

            if (DateUtils.formateDateTime(new Date()).compareTo(user.getExpireTime())>0){
                //用户名过期
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("用户名过期");
            }else if (Contants.RETURN_OBJECT_CODE_FAIL.equals(user.getLockState())){
                //用户被锁定
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("用户被锁定");
            }else if (!user.getAllowIps().contains(request.getRemoteAddr())){
                //不在常用ip中，ip已被锁定
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("不在常用ip中，ip已被锁定");
            }else {
                //可以登陆
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                session.setAttribute(Contants.SESSION_USER, user);
                //如果需要记住密码，则往外写cookie
                if ("true".equals(isRemPwd)){
                    Cookie c1 = new Cookie("loginAct", user.getLoginAct());
                    Cookie c2 = new Cookie("loginPwd", user.getLoginPwd());
                    c1.setMaxAge(10*24*60*60);
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    response.addCookie(c2);
                }else { //删除掉没有过期的cookie
                    Cookie c1 = new Cookie("loginAct","1");
                    Cookie c2 = new Cookie("loginPwd","1");
                    c1.setMaxAge(0);
                    c2.setMaxAge(0);
                    response.addCookie(c1);
                    response.addCookie(c2);
                }
            }
        }
        return returnObject;
    }


    @RequestMapping("/settings/qx/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //清空cookie
        Cookie c1 = new Cookie("loginAct","1");
        Cookie c2 = new Cookie("loginPwd","1");
        c1.setMaxAge(0);
        c2.setMaxAge(0);
        response.addCookie(c1);
        response.addCookie(c2);
        //销毁session
        session.invalidate();
        //重定向
        return "redirect:/";
    }

}
