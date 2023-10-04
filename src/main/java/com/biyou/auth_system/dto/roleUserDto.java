package com.biyou.auth_system.dto;

import lombok.Data;

import java.util.List;

@Data
public class roleUserDto {
    private Integer userId;

    private List<Integer> roleIds;
}
