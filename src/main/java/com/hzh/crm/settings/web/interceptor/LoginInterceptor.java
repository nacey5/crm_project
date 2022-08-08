package com.hzh.crm.settings.web.interceptor;

import com.hzh.crm.commons.contans.Contants;
import com.hzh.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author DAHUANG
 * @date 2022/3/12
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果用户没有登陆成功，跳转到登陆页面
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute(Contants.SESSION_USER);
        if (user==null){
            /**
             **重定向跳转到登陆页面
             * 重定向的时候必须添加项目名称
             */
            response.sendRedirect(request.getContextPath() );
            return  false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
