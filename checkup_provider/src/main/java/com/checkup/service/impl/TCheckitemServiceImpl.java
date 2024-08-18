package com.checkup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.checkup.bean.QueryPageBean;
import com.checkup.pojo.TCheckitem;
import com.checkup.dao.TCheckitemMapper;
import com.checkup.service.TCheckitemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
@Service
public class TCheckitemServiceImpl extends ServiceImpl<TCheckitemMapper, TCheckitem> implements TCheckitemService {

    @Autowired
    private TCheckitemMapper checkitemMapper;

    @Override
    public IPage<TCheckitem> findPage(QueryPageBean pageBean) {
        Page<TCheckitem> page = new Page<>(pageBean.getCurrentPage(), pageBean.getPageSize());
        QueryWrapper<TCheckitem> wrapper = new QueryWrapper<>();
        if (pageBean.getQueryString() != null && pageBean.getQueryString().length() > 0){
            wrapper.eq("code", pageBean.getQueryString());
            wrapper.or();
            wrapper.like("name", pageBean.getQueryString());
        }
        IPage<TCheckitem> iPage = checkitemMapper.selectPage(page, wrapper);
        return iPage;
    }

}
