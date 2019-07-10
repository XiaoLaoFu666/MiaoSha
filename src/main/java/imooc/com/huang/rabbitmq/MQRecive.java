package imooc.com.huang.rabbitmq;


import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imooc.com.huang.co.GoodsVo;
import imooc.com.huang.domain.MiaoShaUser;
import imooc.com.huang.domain.MiaoshaOrder;
import imooc.com.huang.redis.RedisService;
import imooc.com.huang.service.GoodsService;
import imooc.com.huang.service.MiaoshaService;
import imooc.com.huang.service.OrderService;

@Service
public class MQRecive {
	
	  
		@Autowired
		GoodsService goodsService;
		
		@Autowired
		OrderService orderService;
		
		@Autowired
		MiaoshaService miaoshaService;

		@Autowired
		RedisService redisService;
	
	private static org.slf4j.Logger log = LoggerFactory.getLogger(MQRecive.class);
	
    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
	public void recive(String message) {
		log.info("recive message" + message);
		MiaoshaMessage mm = RedisService.stringToBean(message, MiaoshaMessage.class);
		MiaoShaUser user = mm.getMiaoshaUser();
		long goodsId = mm.getGoodsId();
		GoodsVo goods = goodsService.getGoodsByGoodsID(goodsId);
		int stock = goods.getStockCount();
		if(stock<=0) {  //秒杀失败
			return;
		}
		//判断是否秒杀到了（是否已经参加了秒杀）
		MiaoshaOrder order = orderService.getOrderByUserIdAndGoodsId(user.getId(),goodsId);
		if(order!=null) {
			return;
		}
		//生成秒杀订单
		miaoshaService.miaosha(user, goods);
	}
	
//     @RabbitListener(queues = MQConfig.QUEUE)
//	public void recive(String message) {
//		log.info("recive message" + message);
//	}
//     
//     @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//	public void reciveTopic1(String message) {
//		log.info("recive queue1 message" + message);
//	}
//     
//     @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//	public void reciveTopic2(String message) {
//		log.info("recive queue2 message" + message);
//	}
//     
//     @RabbitListener(queues = MQConfig.HANDER_QUEUE)
// 	public void reciveHeader(byte[] message) {
// 		log.info("recive header message" + new String(message));
// 	}
}
