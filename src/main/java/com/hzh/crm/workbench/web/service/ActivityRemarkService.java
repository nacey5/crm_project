package com.hzh.crm.workbench.web.service;

import com.hzh.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @author DAHUANG
 * @date 2022/3/22
 */
public interface ActivityRemarkService {
    /**
     *
     * @param activityId
     * @return
     */
    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);

    /**
     *
     * @param remark
     * @return
     */
    int saveCreateActivityRemark(ActivityRemark remark);
}
