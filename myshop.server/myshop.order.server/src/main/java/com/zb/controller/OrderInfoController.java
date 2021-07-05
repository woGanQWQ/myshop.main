package com.zb.controller;

import com.zb.dto.Result;
import com.zb.entity.OrderInfo;
import com.zb.service.OrderInfoService;
import com.zb.util.IdWorker;
import com.zb.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    @Value("${name}")
    private String name;

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;


    @GetMapping("/createorder")
    public Result createorder(String msg, Integer money, HttpServletRequest request) throws Exception {
//        kafkaTemplate.send("dmservice",  "调用下单为服务器,msg:" + msg + "money:" + money);
        /*String authorization = request.getHeader("Authorization");
        System.out.println(authorization + "<====================");
        Map<String, String> stringStringMap = TokenDecode.dcodeToken(authorization.split(" ")[1]);
        System.out.println("============>" + stringStringMap);
        */
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        System.out.println("=============>>>>" + userInfo);


        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(Long.parseLong(IdWorker.getId()));
        orderInfo.setMessage(msg);
        orderInfo.setMoney(money);
        orderInfoService.insertOrder(orderInfo);
        return Result.succ(name + "下单成功！");
    }

    @GetMapping("/test")
    public Result test() throws Exception {
        System.out.println("Test");
        return Result.succ("不要token的方法");
    }

}
