package com.example.mphigh.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
public class AssetclassParam {


    @TableId(value = "acid", type = IdType.AUTO)
    private Integer acid;

    private String acname;


}
