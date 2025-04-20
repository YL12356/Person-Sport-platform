package com.lovesport.sportplay.controller;

import com.alibaba.fastjson.JSON;
import com.lovesport.sportplay.dao.UserDao;
import com.lovesport.sportplay.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class RegisterController {

    @Autowired
    UserDao userDao;

    @RequestMapping("/register")
    public String register(@RequestBody User user) {
        String flag = "error";
        String message = "注册失败，请重试！";
        User existingUser = userDao.getUserByUsername(user.getUsername());
        if (existingUser == null) {
            boolean state = true; // 默认状态为1
            user.setState(state);
            int result = userDao.addUser(user);
            if (result > 0) {
                flag = "ok";
                message = "注册成功！！！";
            }
        } else {
            message = "用户名已存在，请选择其他用户名！";
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("flag", flag);
        res.put("message", message);
        String res_json = JSON.toJSONString(res);
        return res_json;
    }
}
