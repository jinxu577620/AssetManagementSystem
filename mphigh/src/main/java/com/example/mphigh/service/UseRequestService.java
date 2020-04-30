package com.example.mphigh.service;

import com.example.mphigh.entity.UseRequest;
import com.example.mphigh.entity.User;
import com.example.mphigh.result.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-26
 */
public interface UseRequestService extends IService<UseRequest> {
    Result get(String uid, String amauthority, Integer pageIndex, Integer pageSize);
}
