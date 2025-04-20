package com.lovesport.sportplay.dao;

import com.lovesport.sportplay.bean.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsDao {
    List<Goods> getAllGoods();
    List<Goods> getGoodsByCategory(@Param("category") String category);
    void addGoods(Goods goods);
    void deleteGoods(@Param("id") int id);
}
