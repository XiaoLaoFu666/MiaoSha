 package imooc.com.huang.controller;

 
 
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * 通过rabbit把同步转换成异步访问
 * 
 * 秒杀接口优化
 * 思路：减小数据库访问
 * 1.系统初始化，把商品库存数量存入Redis
 * 2.收到请求，Redis预减库存，如果库存不足，直接返回失败
 * 3.请求入队，返回正在排队中
 * 4.请求出队，生成订单，减少库存
 * 5.客户端轮询，判断是否秒杀成功
 * 
 * **/
 
 
 
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import imooc.com.huang.co.GoodsVo;
import imooc.com.huang.domain.MiaoShaUser;
import imooc.com.huang.domain.MiaoshaOrder;
import imooc.com.huang.domain.OrderInfo;
import imooc.com.huang.rabbitmq.MQSender;
import imooc.com.huang.rabbitmq.MiaoshaMessage;
import imooc.com.huang.redis.GoodsKey;
import imooc.com.huang.redis.RedisService;
import imooc.com.huang.result.CodeMsg;
import imooc.com.huang.result.Result;
import imooc.com.huang.service.GoodsService;
import imooc.com.huang.service.MiaoshaService;
import imooc.com.huang.service.OrderService;

@Controller
@RequestMapping("/miaosha")
public class MiaoShaController implements InitializingBean {
    
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	MiaoshaService miaoshaService;

	@Autowired
	RedisService redisService;
	
	
	@Autowired
	MQSender sender;
	
	/*
	 * 系统初始化
	 * 在系统启动时将商品加载到Redis缓存中
	 * **/
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		List<GoodsVo> goodsList = goodsService.getMiaoShaoGoods();
		if(goodsList==null) {
			return;
		}
		for(GoodsVo goods :goodsList) {
			redisService.set(GoodsKey.getMiaoShaGoodsStock, ""+goods.getId(), goods.getStockCount());
		}
		
	}
	
	@RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
	@ResponseBody
	public Result<Integer> miaosha(Model model,MiaoShaUser user,@RequestParam("goodsId") long goodsId) {
		model.addAttribute("user", user);
		if(user==null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		
		//预减库存
		long stock = redisService.decr(GoodsKey.getMiaoShaGoodsStock, ""+goodsId);
		if(stock<0) {
			return Result.error(CodeMsg.MIAOSHA_OVER);
		}
		
		//判断是否秒杀到
		MiaoshaOrder order = orderService.getOrderByUserIdAndGoodsId(user.getId(),goodsId);
		if(order!=null) {
			return Result.error(CodeMsg.REPEATE_MIAOSHA);
		}
		
		//入队咧
		MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
		miaoshaMessage.setMiaoshaUser(user);
		miaoshaMessage.setGoodsId(goodsId);
		sender.sendMiaoShaMessage(miaoshaMessage);
		return Result.success(0);
		//判断库存
//		GoodsVo goods = goodsService.getGoodsByGoodsID(goodsId);
//		int stock = goods.getStockCount();
//		if(stock<=0) {  //秒杀失败
//			return Result.error(CodeMsg.MIAOSHA_OVER);
//		}
//		//判断是否秒杀到了（是否已经参加了秒杀）
//		MiaoshaOrder order = orderService.getOrderByUserIdAndGoodsId(user.getId(),goodsId);
//		if(order!=null) {
//			return Result.error(CodeMsg.REPEATE_MIAOSHA);
//
//		}
//		//完成减少库存操作，下订单，写入秒杀订单（原子操作）
//		OrderInfo orderInfo= miaoshaService.miaosha(user,goods);
//		return Result.success(orderInfo);
		
	}
	/*
	 * orderId:秒杀成功
	 * -1：秒杀失败
	 * 0：排队中
	 * */

	@RequestMapping(value = "/result",method = RequestMethod.GET)
	@ResponseBody
	public Result<Long> miaoshaResult(Model model,MiaoShaUser user,@RequestParam("goodsId") long goodsId) {
		model.addAttribute("user", user);
		if(user==null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		long result = miaoshaService.getMiaoshaResult(user.getId(),goodsId);
		return Result.success(result);
		
	}

	
//	@RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
//	@ResponseBody
//	public Result<OrderInfo> miaosha(Model model,MiaoShaUser user,@RequestParam("goodsId") long goodsId) {
//		model.addAttribute("user", user);
//		if(user==null) {
//			return Result.error(CodeMsg.SESSION_ERROR);
//		}
//		//判断库存
//		GoodsVo goods = goodsService.getGoodsByGoodsID(goodsId);
//		int stock = goods.getStockCount();
//		if(stock<=0) {  //秒杀失败
//			return Result.error(CodeMsg.MIAOSHA_OVER);
//		}
//		//判断是否秒杀到了（是否已经参加了秒杀）
//		MiaoshaOrder order = orderService.getOrderByUserIdAndGoodsId(user.getId(),goodsId);
//		if(order!=null) {
//			return Result.error(CodeMsg.REPEATE_MIAOSHA);
//
//		}
//		//完成减少库存操作，下订单，写入秒杀订单（原子操作）
//		OrderInfo orderInfo= miaoshaService.miaosha(user,goods);
//		return Result.success(orderInfo);
//		
//	}
//	
	
	
//	@RequestMapping(value = "/do_miaosha")
//	public String toMiaosha(Model model,MiaoShaUser user,@RequestParam("goodsId") long goodsId) {
//		model.addAttribute("user", user);
//		if(user==null) {
//			return "login";
//		}
//		//判断库存
//		GoodsVo goods = goodsService.getGoodsByGoodsID(goodsId);
//		int stock = goods.getStockCount();
//		if(stock<=0) {  //秒杀失败
//			model.addAttribute("errormsg", CodeMsg.MIAOSHA_OVER.getMsg());
//			return "miaosha_fail";
//			
//		}
//		//判断是否秒杀到了（是否已经参加了秒杀）
//		MiaoshaOrder order = orderService.getOrderByUserIdAndGoodsId(user.getId(),goodsId);
//		if(order!=null) {
//			model.addAttribute("errormsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
//			return "miaosha_fail";
//
//		}
//		//完成减少库存操作，下订单，写入秒杀订单（原子操作）
//		OrderInfo orderInfo= miaoshaService.miaosha(user,goods);
//		model.addAttribute("orderInfo", orderInfo);
//		model.addAttribute("goods", goods);
//		return "Order_detail";
//		
//	}
}
