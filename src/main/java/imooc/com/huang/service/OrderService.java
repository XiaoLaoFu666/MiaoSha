package imooc.com.huang.service;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import imooc.com.huang.co.GoodsVo;
import imooc.com.huang.dao.OrderDao;
import imooc.com.huang.domain.MiaoShaUser;
import imooc.com.huang.domain.MiaoshaOrder;
import imooc.com.huang.domain.OrderInfo;
import imooc.com.huang.redis.OrderKey;
import imooc.com.huang.redis.RedisService;

@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;
	
	@Autowired
	RedisService redisService;
	
	public MiaoshaOrder getOrderByUserIdAndGoodsId(long userId, long goodsId) {
		// TODO Auto-generated method stub
		return orderDao.getOrderByUserIdAndGoodsId( userId,  goodsId);
	}
	
	public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
		return redisService.get(OrderKey.getMiaoShaOrderByUidGid, userId+"_"+goodsId, MiaoshaOrder.class);
	}
	
	@Transactional
	public OrderInfo creatOrder(MiaoShaUser user, GoodsVo goods) {
		// TODO Auto-generated method stub
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		orderDao.Insert(orderInfo);
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGoodsId(goods.getId());
		miaoshaOrder.setOrderId(orderInfo.getId());
		miaoshaOrder.setUserId(user.getId());
		orderDao.InsertMiaoshaOrder(miaoshaOrder);
		
		redisService.set(OrderKey.getMiaoShaOrderByUidGid, user.getId()+"_"+goods.getId(), miaoshaOrder);
		
		return orderInfo;
	}

	public OrderInfo getOrderById(long orderId) {
		// TODO Auto-generated method stub
		return orderDao.getOrderById(orderId);
	}

}
