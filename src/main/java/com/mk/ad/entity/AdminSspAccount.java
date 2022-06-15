package com.mk.ad.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdminSspAccount implements Serializable {
    private Long id;

    private String user_d;

    private String company_name;

    private Integer status;

    private Integer company_type;

    private String license_number;

    private String organization_code;

    private String shareholder;

    private String license_image;

    private Integer docking_way;

    private Integer settlement_way;

    private String profit;

    private String payee;

    private String bank_deposit;

    private String bank_account;

    private Date create_time;

    private Date update_time;

    private Date last_time;

    private Integer is_deleted;

    private static final long serialVersionUID = 1L;

}