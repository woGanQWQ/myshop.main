package com.zb.mapper;
import com.zb.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {

	public OrderInfo getOrderInfoById(@Param(value = "id") Long id)throws Exception;

	public List<OrderInfo>	getOrderInfoListByMap(Map<String,Object> param)throws Exception;

	public Integer getOrderInfoCountByMap(Map<String,Object> param)throws Exception;

	public Integer insertOrderInfo(OrderInfo orderInfo)throws Exception;

	public Integer updateOrderInfo(OrderInfo orderInfo)throws Exception;


}
