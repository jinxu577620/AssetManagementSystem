package com.example.mphigh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-05-09
 */
@Data
@Accessors(chain = true)
public class ApprovalProcess  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    private String pname;

    private String uid1;

    private String uid2;

    private String uid3;

    private String uid4;

    private String uid5;

    private String uid6;

    public ApprovalProcess(String pname, String uid1, String uid2, String uid3, String uid4, String uid5, String uid6) {
        this.pname = pname;
        this.uid1 = uid1;
        this.uid2 = uid2;
        this.uid3 = uid3;
        this.uid4 = uid4;
        this.uid5 = uid5;
        this.uid6 = uid6;
    }
    public ApprovalProcess() {}


}
