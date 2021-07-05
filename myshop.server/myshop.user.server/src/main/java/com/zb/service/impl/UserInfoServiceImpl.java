package com.zb.service.impl;

import com.zb.entity.UserInfo;
import com.zb.mapper.UserInfoMapper;
import com.zb.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Integer updateMoney(UserInfo userInfo) throws Exception {
        return userInfoMapper.updateUserInfo(userInfo);
    }

    @Override
    public UserInfo findUserByName(String name) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("account", name);
        List<UserInfo> userInfoListByMap = userInfoMapper.getUserInfoListByMap(param);
        if (userInfoListByMap != null && userInfoListByMap.size() > 0) {
            return userInfoListByMap.get(0);
        }
        return null;
    }
}
