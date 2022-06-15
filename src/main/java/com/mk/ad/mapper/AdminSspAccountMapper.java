package com.mk.ad.mapper;

import com.mk.ad.entity.AdminSspAccount;

public interface AdminSspAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminSspAccount record);

    int insertSelective(AdminSspAccount record);

    AdminSspAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminSspAccount record);

    int updateByPrimaryKey(AdminSspAccount record);
}