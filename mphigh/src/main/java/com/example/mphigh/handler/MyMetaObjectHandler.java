package com.example.mphigh.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.mphigh.config.MybatisPlusConfig;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        if(metaObject.hasSetter("createTime")){
            // 若有该属性，再判断是否已经设置值
            Object createTime = getFieldValByName("createTime", metaObject);
            if (Objects.isNull(createTime)) {
                // 如果为空，表示⾃⼰没设置值，使⽤⾃动填充
                setFieldValByName("createTime",LocalDateTime.now(),metaObject);
            }
        }
        if(metaObject.hasSetter("createBy")){
            // 若有该属性，再判断是否已经设置值
            Object createBy = getFieldValByName("createBy", metaObject);
            if (Objects.isNull(createBy)) {
                // 如果为空，表示⾃⼰没设置值，使⽤⾃动填充登录⽤户名
                setFieldValByName("createBy","loginUser",metaObject);
            }
        }
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        if(metaObject.hasSetter("updateTime")){
            // 若有该属性，再判断是否已经设置值
            Object updateTime = getFieldValByName("updateTime", metaObject);
            if (Objects.isNull(updateTime)) {
                // 如果为空，表示⾃⼰没设置值，使⽤⾃动填充
                setFieldValByName("updateTime", LocalDateTime.now(),
                        metaObject);
            }
        }
        if(metaObject.hasSetter("updateBy")){
            // 若有该属性，再判断是否已经设置值
            Object updateBy = getFieldValByName("updateBy", metaObject);
            if (Objects.isNull(updateBy)) {
                // 如果为空，表示⾃⼰没设置值，使⽤⾃动填充登录⽤户名
                setFieldValByName("updateBy", "loginUser", metaObject);
            }
        }
    }
}