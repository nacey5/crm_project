package com.hzh.crm.workbench.web.controller;

import com.hzh.crm.commons.contans.Contants;
import com.hzh.crm.commons.domain.ReturnObject;
import com.hzh.crm.commons.utils.DateUtils;
import com.hzh.crm.commons.utils.UUIDUtils;
import com.hzh.crm.settings.domain.User;
import com.hzh.crm.workbench.domain.ActivityRemark;
import com.hzh.crm.workbench.web.service.ActivityRemarkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author DAHUANG
 * @date 2022/3/23
 */
@Controller
public class ActivityRemarkController {

    @Resource
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    public @ResponseBody Object saveCreateActivityRemark(ActivityRemark remark, HttpSession session){
            User user= (User) session.getAttribute(Contants.SESSION_USER);
            ReturnObject returnObject=new ReturnObject();
            //封装参数
            remark.setId(UUIDUtils.getUUID());
            remark.setCreateTime(DateUtils.formateDateTime(new Date()));
            remark.setCreateBy(user.getId());
            remark.setEditFlag(Contants.REMARK_EDIT_FLAG_NO_EDIT);
            //调用service层方法，保存创建的备注
        try {
            int ret=activityRemarkService.saveCreateActivityRemark(remark);
            if (ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setObject(remark);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后充重试");
            }
        }catch (Exception e){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后充重试");
        }
        return returnObject;
    }
}
