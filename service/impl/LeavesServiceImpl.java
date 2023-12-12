package com.example.springboot.service.impl;

import com.example.springboot.entity.Leaves;
import com.example.springboot.mapper.LeavesMapper;
import com.example.springboot.service.ILeavesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-07-19
 */
@Service
public class LeavesServiceImpl extends ServiceImpl<LeavesMapper, Leaves> implements ILeavesService {

}
