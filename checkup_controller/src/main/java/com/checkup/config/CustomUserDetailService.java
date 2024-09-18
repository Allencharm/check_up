package com.checkup.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.checkup.pojo.TPermission;
import com.checkup.pojo.TRole;
import com.checkup.pojo.TUser;
import com.checkup.service.TUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailService implements UserDetailsService {
    //远程注入
    @Reference
    private TUserService tUserService;

    //用户查询，授予权限，授予角色
    //方法的参数是从登录页面获取到的登录用户名
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //使用用户名查询用户数据
        TUser tUser = tUserService.getOne(username);

        if (tUser == null){
            throw new UsernameNotFoundException("用户名或密码有误");
        }

        Integer tUserId = tUser.getId();

        //把list集合数据(角色和权限)给当前用户绑定
        ArrayList<GrantedAuthority> list = new ArrayList<>();

        //根据查到的用户，查用户的角色，把查到的角色授予当前用户
        List<TRole> roleList = tUserService.getRoleListByUserID(tUserId);

        //角色信息放入集合
        for (TRole tRole : roleList) {
            list.add(new SimpleGrantedAuthority(tRole.getKeyword()));
        }

        //根据查到的用户，查权限，把查到的权限授予当前用户
        List<TPermission> permissionList = tUserService.getPermissonListByUserID(tUserId);

        //把权限信息放入集合
        for (TPermission tPermission : permissionList) {
            list.add(new SimpleGrantedAuthority(tPermission.getKeyword()));
        }

        //返回查到的当前用户信息
        return new User(username, tUser.getPassword(),list);
    }
}
