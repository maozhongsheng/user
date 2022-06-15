package com.mk.ad.mapper.mymapper;

import com.mk.ad.entity.AdminSspAccount;
import com.mk.ad.mapper.AdminSspAccountMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface MyAdminSspAccountMapper extends AdminSspAccountMapper {

    @Select("select * from admin_ssp_account where user_id = #{user_id}")
    List<AdminSspAccount> selectAccountByUserId(String user_id);

}