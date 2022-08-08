package com.hzh.crm.workbench.web.service;

import com.hzh.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @author DAHUANG
 * @date 2022/3/13
 */
public interface ActivityService {

    /**
     *
     * @param activity
     * @return
     */
    int saveCreateActivity(Activity activity);

    /**
     * @param map
     * @return
     */
    List<Activity> queryActivityConditionForPage(Map<String,Object> map);

    /**
     *
     * @param map
     * @return
     */
    int queryCountOfActivityByCondition(Map<String,Object> map);

    /**
     *
     * @param ids
     * @return
     */
    int deleteActivityByIds(String[] ids);

    /**
     *
     * @param id
     * @return
     */
    Activity queryActivityById(String id);

    /**
     *
     * @param activity
     * @return
     */
    int saveEditActivity(Activity activity);

    /**
     *
     * @return
     */
    List<Activity> queryAllActivitys();

    /**
     *
     * @param activityList
     * @return
     */
    int saveCreateActivityByList(List<Activity> activityList);

    /**
     *
     * @param id
     * @return
     */
    Activity queryActivityForDetailByID(String id);
}
