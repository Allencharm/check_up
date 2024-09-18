package com.checkup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.checkup.bean.QueryPageBean;
import com.checkup.entity.TSetmealEntity;
import com.checkup.pojo.TCheckgroup;
import com.checkup.pojo.TSetmeal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
public interface TSetmealService extends IService<TSetmeal> {

    IPage<TSetmeal> findPage(QueryPageBean pageBean);

    void save(TSetmeal tSetmeal, int[] groupIds);

    void delete(int setmealId);

    TSetmealEntity findOne(int setmealId);

    void updateById(TSetmeal tSetmeal, int[] groupIds, String img);

    TSetmealEntity findInfoById(int id);
}
