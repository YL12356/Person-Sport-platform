package com.lovesport.sportplay.dao;

import com.lovesport.sportplay.bean.SportRecords;
import com.lovesport.sportplay.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportRecordsDao {
    // 添加getUsersWithSportRecords方法
    public List<User> getUsersWithSportRecords(@Param("sportname") String sportname, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);
    public int getSportCounts(@Param("sportname") String sportname);
    public int addSport(SportRecords sportRecords);
    public int deleteSport(@Param("id") int id);
    public SportRecords getUpdateSport(@Param("id") int id);
    public int editSport(SportRecords sportRecords);
}
