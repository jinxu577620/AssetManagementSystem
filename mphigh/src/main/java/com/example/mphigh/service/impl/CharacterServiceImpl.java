package com.example.mphigh.service.impl;

import com.example.mphigh.entity.Character;
import com.example.mphigh.mapper.CharacterMapper;
import com.example.mphigh.service.CharacterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author jinxu18829190342@163.com
 * @since 2020-04-23
 */
@Service
public class CharacterServiceImpl extends ServiceImpl<CharacterMapper, Character> implements CharacterService {

}
