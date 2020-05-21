package com.example.mphigh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 验收申请表
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
@Data
@Accessors(chain = true)
public class AcceptRequest {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rid", type = IdType.AUTO)
    private Integer rid;

    private String aname;

    private Integer acid;

    private Integer num;

    private Double price;

    private String annex;

    private String supplier;

    private String evaluate;

    private Integer astate;

    private Integer pid;

    private String manufacturer;

    public AcceptRequest(Integer rid, String aname, Integer acid, Integer num, Double price, String annex,
            String supplier, String evaluate, Integer astate, Integer pid, String manufacturer) {
        this.rid = rid;
        this.aname = aname;
        this.acid = acid;
        this.num = num;
        this.price = price;
        this.annex = annex;
        this.supplier = supplier;
        this.evaluate = evaluate;
        this.astate = astate;
        this.pid = pid;
        this.manufacturer = manufacturer;
    }
    public AcceptRequest() {
        
    }

}
