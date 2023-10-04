package com.biyou.auth_system.dto;

import lombok.Data;

import java.util.List;

@Data
public class roleMenuDto {
    private Integer roleId;

    private List<Integer> menuIds;
}
