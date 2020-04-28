package com.example.mphigh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.mphigh.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 账号表
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Account extends BaseEntity<Account> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uid", type = IdType.ASSIGN_UUID)
    private String uid;

    private String password;


    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

}
