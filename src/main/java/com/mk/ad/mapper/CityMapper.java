package com.mk.ad.mapper;

import com.mk.ad.entity.City;

import java.util.Map;

public interface CityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

    Integer selectid(String s);

    Integer selectendid(Map pream);
}