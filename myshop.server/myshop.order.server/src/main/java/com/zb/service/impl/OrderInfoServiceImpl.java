package com.zb.service.impl;

import com.zb.client.UserInfoFeginClient;
import com.zb.entity.OrderInfo;
import com.zb.mapper.OrderInfoMapper;
import com.zb.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private UserInfoFeginClient userInfoFeginClient;


    @Override
    public Integer insertOrder(OrderInfo orderInfo) throws Exception {
        userInfoFeginClient.updatemoney(10001, orderInfo.getMoney());
        return orderInfoMapper.insertOrderInfo(orderInfo);
    }
}
