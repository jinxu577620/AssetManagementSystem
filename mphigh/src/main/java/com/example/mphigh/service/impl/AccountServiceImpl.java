package com.example.mphigh.service.impl;

import com.example.mphigh.entity.Account;
import com.example.mphigh.mapper.AccountMapper;
import com.example.mphigh.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账号表 服务实现类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-22
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

}
