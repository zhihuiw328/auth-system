package com.biyou.auth_system.util;

import com.biyou.auth_system.model.SysRoleMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertUtil {

    public static Map<String, Object> toMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public static List<Integer> toListId(List<SysRoleMenu> list) {
        List<Integer> res = new ArrayList<>();
        for ( SysRoleMenu item : list) {
            res.add(item.getMenuId());
        }
        return res;
    }
}
