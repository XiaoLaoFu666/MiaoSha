package imooc.com.huang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import imooc.com.huang.co.GoodsVo;
import imooc.com.huang.domain.Goods;
import imooc.com.huang.domain.MiaoShaUser;
import imooc.com.huang.domain.MiaoshaOrder;
import imooc.com.huang.domain.OrderInfo;
import imooc.com.huang.redis.MiaoShaKey;
import imooc.com.huang.redis.RedisService;

@Service
public class MiaoshaService {
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	RedisService redisService;
	
    @Transactional
	public OrderInfo miaosha(MiaoShaUser user, GoodsVo goods) {
		// TODO Auto-generated method stub
    	boolean success = goodsService.reduceStock(goods); 
    	if(success) {
    		return orderService.creatOrder(user,goods);
    		}else {
    			setGoodsOver(goods.getId());
    			return null;
    		}
	}

	private void setGoodsOver(Long goodsId) {
		// TODO Auto-generated method stub
		redisService.set(MiaoShaKey.isGoodsOver, ""+goodsId, true);
	}

	public long getMiaoshaResult(Long userId, long goodsId) {
		// TODO Auto-generated method stub
		MiaoshaOrder order =  orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
		if(order!=null) {
			return order.getOrderId();
		}else {
			boolean isOver = getGoodsOver(goodsId);
			if(isOver) {
				return -1;
			}else {
				return 0;
			}
		}
				
	}

	private boolean getGoodsOver(long goodsId) {
		// TODO Auto-generated method stub
		return redisService.exists(MiaoShaKey.isGoodsOver, ""+goodsId);
	}

}
