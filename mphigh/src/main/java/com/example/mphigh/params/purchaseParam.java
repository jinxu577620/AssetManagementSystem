package com.example.mphigh.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.val;

@Data
@Accessors(chain = true)
public class purchaseParam {
    @TableId(value = "pmid", type = IdType.AUTO)
    private Integer pmid;
    private String pmname;

}