package com.lovesport.sportplay.controller;

import com.alibaba.fastjson.JSON;
import com.lovesport.sportplay.bean.QueryInfo;
import com.lovesport.sportplay.dao.UserDao;
import com.lovesport.sportplay.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserDao uDao;

    @RequestMapping("/alluser")
    public String getUserList(QueryInfo queryInfo){
//        获取最大列表数和当前编号
        int nunbers = uDao.getUserCounts("%"+queryInfo.getQuery()+"%");
        int pageStart = (queryInfo.getPageNum()-1)* queryInfo.getPageSize();

        List<User> users = uDao.getAllUser("%"+queryInfo.getQuery()+"%",pageStart,queryInfo.getPageSize());
        HashMap<String,Object> res = new HashMap<>();
        res.put("numbers",nunbers);
        res.put("data",users);
        String res_string = JSON.toJSONString(res);
        return res_string;
    }

    @RequestMapping("/userstate")
    public String updateUserState(@RequestParam("id") Integer id,@RequestParam("state") Boolean state){

        int i = uDao.updateState(id,state);
        return i > 0? "success":"error";
    }

    @RequestMapping("/adduser")
    public String addUser(@RequestBody User user){
        user.setRole("普通用户");
        user.setState(false);
        int i = uDao.addUser(user);
        return i > 0?"success":"error";
    }

    @RequestMapping("/deleteuser")
    public String deleteUser(int id){
        int i = uDao.deleteUser(id);
        return i > 0?"success":"error";
    }

    @RequestMapping("/getupdate")
    public String getUpdateUser(int id){
        User user = uDao.getUpdateUser(id);
        return JSON.toJSONString(user);
    }

    @RequestMapping("/edituser")
    public String editUser(@RequestBody User user){
        int i = uDao.editUser(user);
        return i > 0?"success":"error";
    }
}
