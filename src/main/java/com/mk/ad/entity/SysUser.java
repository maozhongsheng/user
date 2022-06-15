package com.mk.ad.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SysUser implements Serializable {
    private String id;

    private String username;

    private String salt;

    private String password;

    private String phone;

    private String deptId;

    private String deptName;

    private String realName;

    private String nickName;

    private String email;

    private Integer status;

    private Integer sex;

    private Integer deleted;

    private String createId;

    private String updateId;

    private Integer createWhere;

    private Date createTime;

    private Date updateTime;

    private String address;


    private String roleId;

    private String roleName;

    private String createT;

    private String role_jurisdiction;

    private Integer sysType;

    private Date last_time;

    private String pwd;

    private Integer isUp;

    private static final long serialVersionUID = 1L;

}