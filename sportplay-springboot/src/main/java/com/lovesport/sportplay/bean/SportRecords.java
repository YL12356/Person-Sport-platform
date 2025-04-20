package com.lovesport.sportplay.bean;

import java.util.Date;
import java.util.List;

public class SportRecords {
    private int id;
    private int uid; // 用户ID
    private String sportname; // 运动名称
    private Date startime; // 开始时间
    private Date endtime; // 结束时间
    private int duration; // 运动时长（分钟）
    private String username;
    // 无参构造器
    public SportRecords() {
    }

    public SportRecords(int id, int uid, String sportname, Date startime, Date endtime, int duration,String username) {
        this.id = id;
        this.uid = uid;
        this.sportname = sportname;
        this.startime = startime;
        this.endtime = endtime;
        this.duration = duration;
        this.username = username;
    }

    public int getId() {
        return id;
    }
    public int getUid() {
        return uid;
    }
    public String getSportname() {
        return sportname;
    }
    public Date getStartime() {
        return startime;
    }
    public Date getEndtime() {
        return endtime;
    }
    public int getDuration() {
        return duration;
    }
    public String getUsername() {
        return username;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public void setSportname(String sportname) {
        this.sportname = sportname;
    }
    public void setStartime(Date startime) {
        this.startime = startime;
    }
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "SportRecords{" +
                "id=" + id +
                ", uid=" + uid +
                ", sportname='" + sportname + '\'' +
                ", startime=" + startime +
                ", endtime=" + endtime +
                ", duration=" + duration +
                ", username='" + username + '\'' +
                '}';
    }
}
