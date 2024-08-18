package com.checkup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.checkup.bean.QueryPageBean;
import com.checkup.dao.TSetmealCheckgroupMapper;
import com.checkup.entity.TSetmealEntity;
import com.checkup.pojo.TCheckgroup;
import com.checkup.pojo.TSetmeal;
import com.checkup.dao.TSetmealMapper;
import com.checkup.pojo.TSetmealCheckgroup;
import com.checkup.service.TCheckgroupService;
import com.checkup.service.TSetmealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
@Service(interfaceClass = TSetmealService.class)
public class TSetmealServiceImpl extends ServiceImpl<TSetmealMapper, TSetmeal> implements TSetmealService {

    @Autowired
    private TSetmealMapper tSetmealMapper;

    @Autowired
    private TSetmealCheckgroupMapper tSetmealCheckgroupMapper;

    @Value("${setmeal_img_path}")
    private String path;

    @Override
    public IPage<TSetmeal> findPage(QueryPageBean pageBean) {
        Page<TSetmeal> page = new Page<>(pageBean.getCurrentPage(), pageBean.getPageSize());
        QueryWrapper<TSetmeal> wrapper = new QueryWrapper<>();
        if (pageBean.getQueryString() != null && pageBean.getQueryString().length() > 0){
            wrapper.eq("code", pageBean.getQueryString());
            wrapper.or();
            wrapper.like("name", pageBean.getQueryString());
            wrapper.or();
            wrapper.like("helpCode", pageBean.getQueryString());
        }
        IPage<TSetmeal> iPage = tSetmealMapper.selectPage(page, wrapper);
        return iPage;
    }

    //保存套餐和中间表数据
    @Override
    public void save(TSetmeal tSetmeal, int[] groupIds) {
        String helpcode = tSetmeal.getHelpcode();
        tSetmeal.setHelpcode(helpcode.toUpperCase());
        tSetmealMapper.insert(tSetmeal);
        Integer id = tSetmeal.getId();
        for (int groupId : groupIds) {
            TSetmealCheckgroup tSetmealCheckgroup = new TSetmealCheckgroup();
            tSetmealCheckgroup.setSetmealId(id);
            tSetmealCheckgroup.setCheckgroupId(groupId);
            tSetmealCheckgroupMapper.insert(tSetmealCheckgroup);
        }
    }

    @Override
    @Transactional
    public void delete(int setmealId) {
        TSetmeal tSetmeal = tSetmealMapper.selectById(setmealId);
        String img = tSetmeal.getImg();
        File file = new File(path, img);
        file.delete();
        QueryWrapper<TSetmealCheckgroup> wrapper = new QueryWrapper<>();
        wrapper.eq("setmeal_id", setmealId);
        tSetmealCheckgroupMapper.delete(wrapper);
        tSetmealMapper.deleteById(setmealId);

    }

    @Override
    public TSetmealEntity findOne(int setmealId) {
        TSetmeal tSetmeal = tSetmealMapper.selectById(setmealId);
        LambdaQueryWrapper<TSetmealCheckgroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TSetmealCheckgroup::getSetmealId, setmealId);
        wrapper.select(TSetmealCheckgroup::getCheckgroupId);
        List<Object> objects = tSetmealCheckgroupMapper.selectObjs(wrapper);
        TSetmealEntity tSetmealEntity = new TSetmealEntity();
        BeanUtils.copyProperties(tSetmeal, tSetmealEntity);
        tSetmealEntity.setCheckgroupIds(objects);
        return tSetmealEntity;
    }

    @Override
    public void updateById(TSetmeal setmeal, int[] groupIds, String img) {
        Integer id = setmeal.getId();
        if (!img.equals(setmeal.getImg())){
            File file = new File(path, img);
            file.delete();
        }

        QueryWrapper<TSetmealCheckgroup> wrapper = new QueryWrapper<>();
        wrapper.eq("setmeal_id", id);
        tSetmealCheckgroupMapper.delete(wrapper);
        for (int groupId : groupIds) {
            TSetmealCheckgroup tSetmealCheckgroup = new TSetmealCheckgroup();
            tSetmealCheckgroup.setSetmealId(id);
            tSetmealCheckgroup.setCheckgroupId(groupId);
            tSetmealCheckgroupMapper.insert(tSetmealCheckgroup);
        }
        tSetmealMapper.updateById(setmeal);
    }
}
