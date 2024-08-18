package com.checkup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.checkup.bean.QueryPageBean;
import com.checkup.pojo.TCheckitem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
public interface TCheckitemService extends IService<TCheckitem> {

    IPage<TCheckitem> findPage(QueryPageBean pageBean);
}
