package com.adk.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserAllInfoVo {
    private String id;

    private String account;

    private Integer admin;

    private String avatar;

    private String createDate;

    private String email;

    private String mobilePhoneNumber;

    private String nickname;
}
