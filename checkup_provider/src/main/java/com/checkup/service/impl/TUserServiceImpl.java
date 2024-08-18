package com.checkup.service.impl;

import com.checkup.pojo.TUser;
import com.checkup.dao.TUserMapper;
import com.checkup.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

}
