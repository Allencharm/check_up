package com.checkup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.checkup.dao.*;
import com.checkup.pojo.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.checkup.service.TUserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private TUserMapper tUserMapper;

    @Autowired
    private TUserRoleMapper tUserRoleMapper;

    @Autowired
    private TRoleMapper tRoleMapper;

    @Autowired
    private TRolePermissionMapper tRolePermissionMapper;

    @Autowired
    private TPermissionMapper tPermissionMapper;

    @Override
    public List<TRole> getRoleListByUserID(Integer tUserId) {
        LambdaQueryWrapper<TUserRole> tUserRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tUserRoleLambdaQueryWrapper.eq(TUserRole::getUserId, tUserId);
        tUserRoleLambdaQueryWrapper.select(TUserRole::getRoleId);
        List<Integer> roleids = tUserRoleMapper.selectObjs(tUserRoleLambdaQueryWrapper).stream().map(o -> (Integer) o).collect(Collectors.toList());
        return tRoleMapper.selectBatchIds(roleids);
    }

    @Override
    public List<TPermission> getPermissonListByUserID(Integer tUserId) {
        LambdaQueryWrapper<TUserRole> tUserRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tUserRoleLambdaQueryWrapper.eq(TUserRole::getUserId, tUserId);
        tUserRoleLambdaQueryWrapper.select(TUserRole::getRoleId);
        List<Integer> roleids = tUserRoleMapper.selectObjs(tUserRoleLambdaQueryWrapper).stream().map(o -> (Integer) o).collect(Collectors.toList());

        LambdaQueryWrapper<TRolePermission> tRolePermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tRolePermissionLambdaQueryWrapper.in(TRolePermission::getRoleId, roleids);
        tRolePermissionLambdaQueryWrapper.select(TRolePermission::getPermissionId);
        List<Integer> permissonids = tRolePermissionMapper.selectObjs(tRolePermissionLambdaQueryWrapper).stream().map(o -> (Integer) o).collect(Collectors.toList());

        return tPermissionMapper.selectBatchIds(permissonids);
    }

    @Override
    public TUser getOne(String username) {
        QueryWrapper<TUser> tUserQueryWrapper = new QueryWrapper<>();
        tUserQueryWrapper.eq("username", username);
        TUser tUser = tUserMapper.selectOne(tUserQueryWrapper);
        return tUser;
    }
}
