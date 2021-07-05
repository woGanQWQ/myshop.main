package com.zb.service;

import com.zb.entity.UserInfo;

public interface UserInfoService {
    public Integer updateMoney(UserInfo userInfo)throws Exception;

    public UserInfo findUserByName(String name) throws Exception;
}
