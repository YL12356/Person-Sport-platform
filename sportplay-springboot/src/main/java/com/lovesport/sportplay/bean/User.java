package com.lovesport.sportplay.bean;

import java.util.List;

//用户实体
public class User {
    private int id;
    private String username;    //用户名
    private String password;    //密码
    private String email;       //邮箱
    private String role;        //角色
    private boolean state;      //状态
    private List<SportRecords> sportRecords;

    //无参构造器（必须有！！！）
    public User(){

    }

    public User(String username, String password, String email, String role, boolean state,List<SportRecords> sportRecords) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.state = state;
        this.sportRecords=sportRecords;
    }
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getRole() {
        return role;
    }
    public boolean getState() {
        return state;
    }
    public List<SportRecords> getSportRecords() {
        return sportRecords;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    public void setSr(List<SportRecords> sportRecords) {
        this.sportRecords = sportRecords;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", state=" + state +
                ", sportRecords=" + sportRecords +
                '}';
    }


}
