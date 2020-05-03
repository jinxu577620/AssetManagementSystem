package com.example.mphigh.mapper;

import com.example.mphigh.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
public interface UserMapper extends BaseMapper<User> {
    @Delete("delete from user where uid = #{user.uid}")
    void delete(@Param("user") User user);
}
