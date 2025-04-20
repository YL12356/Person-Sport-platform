package com.lovesport.sportplay.controller;

import com.lovesport.sportplay.bean.Goods;
import com.lovesport.sportplay.dao.GoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GoodsController {

    @Autowired
    private GoodsDao gDao;

    @GetMapping("/goods")
    public Map<String, Object> getGoods(@RequestParam(required = false) String category) {
        List<Goods> gList;
        if (category == null || category.isEmpty()) {
            gList = gDao.getAllGoods();
        } else {
            gList = gDao.getGoodsByCategory(category);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("goodsList", gList);
        response.put("selectedCategory", category);
        return response;
    }

    @PostMapping("/addgoods")
    public String addGoods(@RequestBody Goods goods) {
        try {
            gDao.addGoods(goods);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @DeleteMapping("/deletegoods")
    public String deleteGoods(@RequestParam int id) {
        try {
            gDao.deleteGoods(id);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
