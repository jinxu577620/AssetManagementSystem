package com.example.mphigh.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity<T extends Model<?>> extends Model<T> implements
        Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;//⾃动
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE) // 新增和更新执⾏
    private String updateBy;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "remark")
    private String remark;
    @Version // 乐观锁
    private Integer version;
    @TableLogic // 逻辑删除
    @TableField(select = false) // 查询的时候就会⾃动忽略该字段
    private Integer deleted; }