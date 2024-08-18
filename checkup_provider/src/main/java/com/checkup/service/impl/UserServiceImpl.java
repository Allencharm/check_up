package com.checkup.service.impl;

import com.checkup.service.UserService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author ldq
 * @version 1.0
 * @date 2024-4-4 11:45
 * @Description: UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getString() {
        return "test";
    }
}
