package imooc.com.huang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import imooc.com.huang.co.GoodsVo;
import imooc.com.huang.co.OrderDetailVo;
import imooc.com.huang.domain.MiaoShaUser;
import imooc.com.huang.domain.OrderInfo;
import imooc.com.huang.result.CodeMsg;
import imooc.com.huang.result.Result;
import imooc.com.huang.service.GoodsService;
import imooc.com.huang.service.MiaoShaUserService;
import imooc.com.huang.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
     
	@Autowired
	MiaoShaUserService userService;
	
	@Autowired
	imooc.com.huang.redis.RedisService redisService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	
	@RequestMapping("/detail")
	@ResponseBody
	public Result<OrderDetailVo> info(Model model,MiaoShaUser user,
			@RequestParam("orderId") long orderId){
		if(user==null) {
			return Result.error(CodeMsg.SESSION_ERROR);
			
		}
		OrderInfo order =orderService.getOrderById(orderId); 
		if(order == null) {
			return Result.error(CodeMsg.ORDER_NOT_EXITS);
		}
		long goodsId =order.getGoodsId();
		GoodsVo goods = goodsService.getGoodsByGoodsID(goodsId);
		OrderDetailVo vo = new OrderDetailVo();
		vo.setGoods(goods);
		vo.setOrder(order);
		return Result.success(vo);
		
	}
}
