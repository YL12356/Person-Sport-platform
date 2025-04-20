package com.lovesport.sportplay.dao;

import com.lovesport.sportplay.bean.Introduction;

import java.util.List;

public interface IntroductionDao {
    List<Introduction> getAllIntroductions();
    Introduction getIntroductionById(int kid);
}
