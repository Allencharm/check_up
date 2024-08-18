package com.checkup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.checkup.bean.QueryPageBean;
import com.checkup.dao.TCheckgroupCheckitemMapper;
import com.checkup.entity.TCheckGroupEntity;
import com.checkup.pojo.TCheckgroup;
import com.checkup.dao.TCheckgroupMapper;
import com.checkup.pojo.TCheckgroupCheckitem;
import com.checkup.service.TCheckgroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
@Service(interfaceClass = TCheckgroupService.class)
public class TCheckgroupServiceImpl extends ServiceImpl<TCheckgroupMapper, TCheckgroup> implements TCheckgroupService {

    @Autowired
    private TCheckgroupMapper tCheckgroupMapper;

    @Autowired
    private TCheckgroupCheckitemMapper tCheckgroupCheckitemMapper;

    @Override
    public IPage<TCheckgroup> findPage(QueryPageBean pageBean) {
        Page<TCheckgroup> page = new Page<>(pageBean.getCurrentPage(), pageBean.getPageSize());
        QueryWrapper<TCheckgroup> wrapper = new QueryWrapper<>();
        if (pageBean.getQueryString() != null && pageBean.getQueryString().length() > 0){
            wrapper.eq("code", pageBean.getQueryString());
            wrapper.or();
            wrapper.like("name", pageBean.getQueryString());
            wrapper.or();
            wrapper.like("helpCode", pageBean.getQueryString());
        }
        IPage<TCheckgroup> iPage = tCheckgroupMapper.selectPage(page, wrapper);
        return iPage;
    }

    //添加检查组，添加检查组和检查项中间表数据
    @Override
    @Transactional
    public void save(TCheckgroup tCheckgroup, int[] itemIds) {
        String helpcode = tCheckgroup.getHelpcode();
        tCheckgroup.setHelpcode(helpcode.toUpperCase());
        //保存检查组信息
        tCheckgroupMapper.insert(tCheckgroup);
        //获取检查组id
        Integer id = tCheckgroup.getId();
        //保存检查组和检查项中间表数据
        for (int itemId : itemIds){
            TCheckgroupCheckitem tCheckgroupCheckitem = new TCheckgroupCheckitem();
            tCheckgroupCheckitem.setCheckgroupId(id);
            tCheckgroupCheckitem.setCheckitemId(itemId);
            tCheckgroupCheckitemMapper.insert(tCheckgroupCheckitem);
        }
    }

    //删除中间表，检查组
    @Override
    public void deleteOne(int groupId) {
        QueryWrapper<TCheckgroupCheckitem> wrapper = new QueryWrapper<>();
        wrapper.eq("checkgroup_id", groupId);
        tCheckgroupCheckitemMapper.delete(wrapper);
        tCheckgroupMapper.deleteById(groupId);
    }

    @Override
    public TCheckGroupEntity findOne(int groupId) {
        TCheckgroup tCheckgroup = tCheckgroupMapper.selectById(groupId);
        LambdaQueryWrapper<TCheckgroupCheckitem> wrapper = new LambdaQueryWrapper<>();
        //条件
        wrapper.eq(TCheckgroupCheckitem::getCheckgroupId, groupId);
        //查询的返回值
        wrapper.select(TCheckgroupCheckitem::getCheckitemId);
        List<Object> ids = tCheckgroupCheckitemMapper.selectObjs(wrapper);
        TCheckGroupEntity tCheckGroupEntity = new TCheckGroupEntity();
        BeanUtils.copyProperties(tCheckgroup,tCheckGroupEntity);
        tCheckGroupEntity.setCheckitemIds(ids);
        return tCheckGroupEntity;
    }

    @Override
    public void edit(TCheckgroup tCheckgroup, int[] itemIds) {
        String helpcode = tCheckgroup.getHelpcode();
        tCheckgroup.setHelpcode(helpcode.toUpperCase());
        //保存检查组信息
        tCheckgroupMapper.updateById(tCheckgroup);
        //获取检查组id
        Integer id = tCheckgroup.getId();
        QueryWrapper<TCheckgroupCheckitem> wrapper = new QueryWrapper<>();
        wrapper.eq("checkgroup_id", id);
        tCheckgroupCheckitemMapper.delete(wrapper);
        //保存检查组和检查项中间表数据
        for (int itemId : itemIds){
            TCheckgroupCheckitem tCheckgroupCheckitem = new TCheckgroupCheckitem();
            tCheckgroupCheckitem.setCheckgroupId(id);
            tCheckgroupCheckitem.setCheckitemId(itemId);
            tCheckgroupCheckitemMapper.insert(tCheckgroupCheckitem);
        }
    }


}
