package com.checkup.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.checkup.bean.Result;
import com.checkup.entity.TOrderEntity;
import org.apache.dubbo.config.annotation.Service;
import com.checkup.dao.TMemberMapper;
import com.checkup.dao.TOrdersettingMapper;
import com.checkup.dao.TSetmealMapper;
import com.checkup.pojo.TMember;
import com.checkup.pojo.TOrder;
import com.checkup.dao.TOrderMapper;
import com.checkup.pojo.TOrdersetting;
import com.checkup.pojo.TSetmeal;
import com.checkup.service.TOrderService;
import com.checkup.util.MessageConstant;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
@Service(interfaceClass = TOrderService.class)
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private TOrdersettingMapper ordersettingMapper;
    @Autowired
    private TMemberMapper memberMapper;
    @Autowired
    private TOrderMapper orderMapper;
    @Autowired
    private TSetmealMapper setmealMapper;

    //下单预约
    @Override
    @Transactional
    public Result submitOrder(Map map) {
        String orderDate = (String) map.get("orderDate");
        //指定日期是否可以预约
        QueryWrapper<TOrdersetting>wrapper = new QueryWrapper<>();
        wrapper.eq("orderDate",orderDate);
        TOrdersetting ordersetting = ordersettingMapper.selectOne(wrapper);
        if(ordersetting == null){
            return new Result(false, MessageConstant.ORDER_NOT,null);
        }
        //指定日期是否预约已满
        Integer number = ordersetting.getNumber();
        Integer reservations = ordersetting.getReservations();
        if(reservations >= number){
            return new Result(false,MessageConstant.ORDER_FULL,null);
        }

        //通过电话号码获取会员id -- 可能获取到，可能获取不到(把当前信息作为会员信息保存，进而获取到会员id)
        String telephone = (String) map.get("telephone");
        QueryWrapper<TMember>memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("phoneNumber",telephone);
        TMember tMember = memberMapper.selectOne(memberQueryWrapper);
        int mid;
        if(tMember == null){
            TMember member = new TMember();
            member.setName(map.get("name")+"");
            member.setSex(map.get("sex")+"");
            member.setIdcard(map.get("idCard")+"");
            member.setPhonenumber(telephone);
            member.setRegtime(LocalDate.now());
            memberMapper.insert(member);
            mid = member.getId();
        }else {
            mid = tMember.getId();
        }
        //当前预约人是否重复预约 -- 会员id、套餐id、预约日期做条件
        QueryWrapper<TOrder>orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("member_id",mid);
        orderQueryWrapper.eq("orderDate",orderDate);
        orderQueryWrapper.eq("setmeal_id",map.get("setmealId"));
        TOrder tOrder = orderMapper.selectOne(orderQueryWrapper);
        if(tOrder != null){
            return new Result(false,MessageConstant.HAS_ORDERED,null);
        }

        //预约成功 -- 保存订单信息、修改已预约人数
        //修改已预约人数
        ordersetting.setReservations(ordersetting.getReservations() + 1);
        ordersettingMapper.updateById(ordersetting);

        //增加订单
        TOrder order = new TOrder();
        order.setMemberId(mid);
        order.setOrderdate(LocalDate.parse(orderDate));
        order.setOrdertype("微信公众号预约");
        order.setOrderstatus("未到诊");
        String setmealId = map.get("setmealId") + "";
        order.setSetmealId(Integer.parseInt(setmealId));
        orderMapper.insert(order);

        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    //主键查询订单，包含会员名和套餐名
    @Override
    public TOrderEntity findOrderById(int id) {
        TOrder order = orderMapper.selectById(id);
        Integer memberId = order.getMemberId();
        TMember member = memberMapper.selectById(memberId);

        Integer setmealId = order.getSetmealId();
        TSetmeal tSetmeal = setmealMapper.selectById(setmealId);

        TOrderEntity entity = new TOrderEntity();
        BeanUtils.copyProperties(order,entity);
        entity.setMember(member.getName());
        entity.setSetmeal(tSetmeal.getName());

        return entity;
    }
}
