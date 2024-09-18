package com.checkup.service;

import com.checkup.pojo.TPermission;
import com.checkup.pojo.TRole;
import com.checkup.pojo.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
public interface TUserService extends IService<TUser> {

    List<TRole> getRoleListByUserID(Integer tUserId);

    List<TPermission> getPermissonListByUserID(Integer tUserId);

    TUser getOne(String username);
}
