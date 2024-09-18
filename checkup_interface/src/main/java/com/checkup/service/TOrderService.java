package com.checkup.service;

import com.checkup.bean.Result;
import com.checkup.entity.TOrderEntity;
import com.checkup.pojo.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
public interface TOrderService extends IService<TOrder> {

    Result submitOrder(Map map);

    TOrderEntity findOrderById(int id);
}
