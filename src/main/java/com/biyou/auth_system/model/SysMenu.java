package com.biyou.auth_system.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 权限表
 * </p>
 *
 * .
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限父id
     */
    private Integer pid;

    /**
     * 权限名称
     */
    private String menuName;

    /**
     * 权限唯一标识
     */
    private String menuCode;

    /**
     * 权限类型,0:目录;1:菜单;2:按钮
     */
    private Integer menuType;

    /**
     * 权限url
     */
    private String url;

    /**
     * 图标
     */
    private String icon;
    /**
     * 是否外部打开 0否1是
     */
    @TableField("is_outside")
    private String outside;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    @TableField(exist=false)
    private boolean checked;

    @TableField(exist=false)
    private String parentName;

    @TableField(exist=false)
    private boolean last;

    @TableField(exist=false)
    private List<SysMenu> children = new ArrayList<>();

}
