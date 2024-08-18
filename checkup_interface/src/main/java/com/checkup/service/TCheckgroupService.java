package com.checkup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.checkup.bean.QueryPageBean;
import com.checkup.entity.TCheckGroupEntity;
import com.checkup.pojo.TCheckgroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
public interface TCheckgroupService extends IService<TCheckgroup> {

    IPage<TCheckgroup> findPage(QueryPageBean pageBean);

    void save(TCheckgroup tCheckgroup, int[] itemIds);

    void deleteOne(int groupId);

    TCheckGroupEntity findOne(int groupId);

    void edit(TCheckgroup tCheckgroup, int[] itemIds);
}
