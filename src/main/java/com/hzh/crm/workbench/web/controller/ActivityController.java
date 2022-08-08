package com.hzh.crm.workbench.web.controller;

import com.hzh.crm.commons.contans.Contants;
import com.hzh.crm.commons.domain.ReturnObject;
import com.hzh.crm.commons.utils.DateUtils;
import com.hzh.crm.commons.utils.HSSFUtils;
import com.hzh.crm.commons.utils.UUIDUtils;
import com.hzh.crm.settings.domain.User;
import com.hzh.crm.settings.web.service.UserService;
import com.hzh.crm.workbench.domain.Activity;
import com.hzh.crm.workbench.domain.ActivityRemark;
import com.hzh.crm.workbench.web.service.ActivityRemarkService;
import com.hzh.crm.workbench.web.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * @author DAHUANG
 * @date 2022/3/12
 */
@Controller
public class ActivityController {

    @Resource
    private UserService userService;

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        //调用service层方法查询所有的用户
        List<User> userList = userService.queryAllUsers();
        //把数据保存到request中
        request.setAttribute("userList",userList);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public @ResponseBody Object saveCreateActivity(Activity activity, HttpSession session){
        User user   = (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formateDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject=new ReturnObject();
        try{
            int res = activityService.saveCreateActivity(activity);
            if (res>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后再试");
            }
        }catch (Exception e){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后再试");
        }

        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    public @ResponseBody Object queryActivityByConditionForPage(String name,String owner,String startDate,
                                                                String endDate,int pageNo,int pageSize){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        //调用service
        List<Activity> activityList = activityService.queryActivityConditionForPage(map);
        int totalRows = activityService.queryCountOfActivityByCondition(map);
        //根据查询结果生成相应信息
        Map<String,Object> retMap=new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);
        return retMap;
    }

    @RequestMapping("/workbench/activity/deleteActivityIds.do")
    public @ResponseBody Object deleteActivityIds(String[] id){

        ReturnObject returnObject=new ReturnObject();
        //调用service
        try {
            int res = activityService.deleteActivityByIds(id);
            if (res>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试");
        }
        return returnObject;

    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    public @ResponseBody Object queryActivityById(String id){
        Activity activity = activityService.queryActivityById(id);
        //根据查询结果，返回相应信息
        return activity;
    }


    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public @ResponseBody Object saveEditActivity(Activity activity,HttpSession session){
        User user= (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activity.setEditTime(DateUtils.formateDateTime(new Date()));
        activity.setEditBy(user.getId());
        ReturnObject returnObject=new ReturnObject();
        try {
            int res=activityService.saveEditActivity(activity);
            if (res>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后再试");
            }
        }catch (Exception e){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后再试");
        }

        return returnObject;
    }


    @RequestMapping("/workbench/activity/fileDownload.do")
    public void fileDownload(HttpServletResponse response)throws Exception{
        //1设置相应类型
        response.setContentType("application/octet-stream;charset=utf-8");
        //2获取输出流
        OutputStream out = response.getOutputStream();

        //浏览器接收到响应信息之后，默认情况下，直接在显示窗口打开响应信息；即使打不开，也会调用程序打开，只有实在打不开，才会激活文件下载
         //设置响应头信息，使得浏览器在接收到请求的时候直接打开下载窗口
        response.addHeader("Content-Disposition","attachment;filename=mystdentList.xls");

        //读取excel文件(InputStream),输出到浏览器(OutPutStream)
        InputStream is=new FileInputStream("F:\\IdeaServerDirOutPutExcel\\student.xlsx");
        byte[] buff=new byte[256];
        int len=0;
        while ((len=is.read(buff))!=-1){
            out.write(buff,0,len);
        }
        //谁开启谁关闭，关闭资源，out是tomcat创建的，所以不需要自己关
        is.close();
        out.flush();

    }

    @RequestMapping("/workbench/activity/exportAllActivitys.do")
    public void exportAllActivitys(HttpServletResponse response) throws Exception{
        List<Activity> activityList = activityService.queryAllActivitys();
        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");

        for (int i = 0; i <activityList.size() ; i++) {
            Activity activity = activityList.get(i);
            //每次遍历一个activity，生成一行
            row=sheet.createRow(i+1);

            cell = row.createCell(0);
            cell.setCellValue(activity.getId());
            cell = row.createCell(1);
            cell.setCellValue(activity.getOwner());
            cell = row.createCell(2);
            cell.setCellValue(activity.getName());
            cell = row.createCell(3);
            cell.setCellValue(activity.getStartDate());
            cell = row.createCell(4);
            cell.setCellValue(activity.getEndDate());
            cell = row.createCell(5);
            cell.setCellValue(activity.getCost());
            cell = row.createCell(6);
            cell.setCellValue(activity.getDescription());
            cell = row.createCell(7);
            cell.setCellValue(activity.getCreateTime());
            cell = row.createCell(8);
            cell.setCellValue(activity.getCreateBy());
            cell = row.createCell(9);
            cell.setCellValue(activity.getEditTime());
            cell = row.createCell(10);
            cell.setCellValue(activity.getEditBy());
        }

       /* //根据wb对象生成excel文件
        OutputStream os=new FileOutputStream("F:\\IdeaServerDirOutPutExcel\\activityList.xlsx");
        wb.write(os);*/

        /*os.close();
        wb.close();*/

        //把生成的文件下载到用户的客户端
        response.setContentType("application/octet-stream;charset=utf-8");
        OutputStream out = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
       /* InputStream is=new FileInputStream("F:\\IdeaServerDirOutPutExcel\\activityList.xlsx");
        byte[] buff=new byte[256];
        int len=0;
        while ((len=is.read(buff))!=-1){
            out.write(buff,0,len);
        }
        //谁开启谁关闭，关闭资源，out是tomcat创建的，所以不需要自己关
        is.close();*/
        wb.write(out);
        wb.close();
        out.flush();
    }

    @RequestMapping("/workbench/activity/importActivity.do")
    public @ResponseBody Object importActivity(MultipartFile activityFile, HttpSession session){
        User user= (User) session.getAttribute(Contants.SESSION_USER);
        ReturnObject returnObject=new ReturnObject();
        try {
            /*String originalFilename=activityFile.getOriginalFilename();
            File file=new File("F:\\IdeaServerDirOutPutExcel",originalFilename);
            activityFile.transferTo(file);*/
            //解析excel文件
            //InputStream is = new FileInputStream("F:\\IdeaServerDirOutPutExcel\\"+originalFilename);

            InputStream is = activityFile.getInputStream();
            HSSFWorkbook wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row=null;
            HSSFCell cell=null;
            Activity activity=null;
            List<Activity> activityList=new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row=sheet.getRow(i);
                activity=new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtils.formateDateTime(new Date()));
                activity.setCreateBy(user.getId());
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell=row.getCell(i);
                    String cellValue= HSSFUtils.getCellValueForStr(cell);
                    if (j==0){
                        activity.setName(cellValue);
                    }else if (j==1){
                        activity.setStartDate(cellValue);
                    }else if (j==2){
                        activity.setEndDate(cellValue);
                    }else if (j==3){
                        activity.setCost(cellValue);
                    }else if (j==4){
                        activity.setDescription(cellValue);
                    }
                    //每一行所有列表封装完成之后，把activity保存到list中

                }

                activityList.add(activity);
            }
                int ret = activityService.saveCreateActivityByList(activityList);
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setObject(ret);

        }catch (Exception e){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，稍后再试...");
        }

        return returnObject;
    }

    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id,HttpServletRequest request){
        //调用service层方法查询数据
        Activity activity = activityService.queryActivityForDetailByID(id);
        List<ActivityRemark> remarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        //把数据保存到request中
        request.setAttribute("activity",activity);
        request.setAttribute("remarkList",remarkList);
        //请求转发
        return "workbench/activity/detail";
    }

 
}
