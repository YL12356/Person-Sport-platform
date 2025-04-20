package com.lovesport.sportplay.controller;

import com.lovesport.sportplay.bean.Introduction;
import com.lovesport.sportplay.dao.IntroductionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/Introduction")
public class IntroductionController {

    @Autowired
    private IntroductionDao iDao;

    // 获取所有简介
    @GetMapping("/list")
    @ResponseBody
    public List<Introduction> getAllIntroductions() {
        return iDao.getAllIntroductions();
    }

    // 根据ID获取简介
    @GetMapping("/{id}")
    @ResponseBody
    public Introduction getIntroductionById(@PathVariable("id") int id) {
        return iDao.getIntroductionById(id);
    }
}
