package com.lovesport.sportplay.controller;

import com.alibaba.fastjson.JSON;
import com.lovesport.sportplay.bean.QueryInfo;
import com.lovesport.sportplay.bean.SportRecords;
import com.lovesport.sportplay.bean.User;
import com.lovesport.sportplay.dao.SportRecordsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class SportRecordsController {

    @Autowired
    private SportRecordsDao sDao;

    @RequestMapping("/getUsersWithSportRecords")
    public String getUsersWithSportRecords(QueryInfo queryInfo) {
        try {
            int numbers = sDao.getSportCounts("%" + queryInfo.getQuery() + "%");
            int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();

            List<User> users = sDao.getUsersWithSportRecords("%" + queryInfo.getQuery() + "%", pageStart, queryInfo.getPageSize());
            HashMap<String, Object> res = new HashMap<>();
            res.put("numbers", numbers);
            res.put("data", users);
            return JSON.toJSONString(res);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(new HashMap<String, Object>() {{
                put("numbers", 0);
                put("data", null);
            }});
        }
    }

    @RequestMapping("/addsport")
    public String addSport(@RequestBody SportRecords sportRecords) {
        try {
            // 计算运动时长
            sportRecords.setDuration(calculateDurationInHours(sportRecords.getStartime(), sportRecords.getEndtime()));
            int i = sDao.addSport(sportRecords);
            return i > 0 ? "success" : "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/deletesport")
    public String deleteSport(@RequestParam("id") int id) {
        try {
            int i = sDao.deleteSport(id);
            return i > 0 ? "success" : "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/getupdatesport")
    public String getUpdateSport(@RequestParam("id") int id) {
        try {
            SportRecords sportRecords = sDao.getUpdateSport(id);
            return JSON.toJSONString(sportRecords);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(null);
        }
    }

    @RequestMapping("/editsport")
    public String editSport(@RequestBody SportRecords sportRecords) {
        try {
            // 计算运动时长
            sportRecords.setDuration(calculateDurationInHours(sportRecords.getStartime(), sportRecords.getEndtime()));
            int i = sDao.editSport(sportRecords);
            return i > 0 ? "success" : "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    private int calculateDurationInHours(Date startTime, Date endTime) {
        if (startTime != null && endTime != null) {
            long durationInMillis = endTime.getTime() - startTime.getTime();
            return (int) (durationInMillis / (1000 * 60)); // 返回运动时长，单位为分钟
        }
        return 0; // 如果startTime或endTime为null，返回0分钟
    }
}
