package com.biyou.auth_system.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("common field fill[insert]...");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", new Date());
        if (metaObject.hasSetter("updateTime")) {
            metaObject.setValue("updateTime", new Date());
        }

        metaObject.setValue("createUser", "3");
        if (metaObject.hasSetter("updateUser")) {
            metaObject.setValue("updateUser", "3");
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("common field fill[update]...");
        log.info(metaObject.toString());


        metaObject.setValue("updateTime", new Date());
        metaObject.setValue("updateUser", "3");
    }
}
