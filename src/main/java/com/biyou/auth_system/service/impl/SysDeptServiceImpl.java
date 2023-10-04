package com.biyou.auth_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biyou.auth_system.mapper.SysDeptMapper;
import com.biyou.auth_system.model.SysDept;
import com.biyou.auth_system.service.SysDeptService;
import org.springframework.stereotype.Service;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
}
