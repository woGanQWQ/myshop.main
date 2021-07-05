package com.zb.controller;

import com.zb.dto.Result;
import com.zb.entity.UserInfo;
import com.zb.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;


    @GetMapping("/updatemoney/{id}/{money}")
    public Result updatemoney(@PathVariable("id") Integer id, @PathVariable("money") Integer money, HttpServletRequest request) throws Exception {
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization + "<====================");
//        kafkaTemplate.send("dmservice", "aaaaaaaaaaaaaa-"+money);
//        kafkaTemplate.send("dmservice", "调用了用户微服务" + id + "money:" + money);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setMoney(money);
        Integer count = userInfoService.updateMoney(userInfo);
        if (count > 0) {
            return Result.succ("修改成功!");
        } else {
            return Result.fail("修改失败！");
        }
    }

    @PostMapping("/finduser")
    public Result finduser(@RequestParam String name) throws Exception {
        UserInfo user = userInfoService.findUserByName(name);
        return Result.succ("用户详情", user);
    }


}
