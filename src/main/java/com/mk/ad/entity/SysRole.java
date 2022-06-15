package com.mk.ad.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mk.ad.vo.resp.PermissionRespNodeVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysRole {

    private String id;

    private String name;

    private String description;

    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer deleted;

    private String number;

    private List<PermissionRespNodeVO> permissionRespNode;

    private String role_power;

}