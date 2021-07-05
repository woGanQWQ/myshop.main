package com.zb.mapper;
import com.zb.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {

	public UserInfo getUserInfoById(@Param(value = "id") Long id)throws Exception;

	public List<UserInfo>	getUserInfoListByMap(Map<String,Object> param)throws Exception;

	public Integer getUserInfoCountByMap(Map<String,Object> param)throws Exception;

	public Integer insertUserInfo(UserInfo userInfo)throws Exception;

	public Integer updateUserInfo(UserInfo userInfo)throws Exception;


}
