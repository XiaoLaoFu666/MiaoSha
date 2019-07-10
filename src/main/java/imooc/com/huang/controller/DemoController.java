package imooc.com.huang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import imooc.com.huang.domain.User;
import imooc.com.huang.rabbitmq.MQSender;
import imooc.com.huang.redis.RedisService;
import imooc.com.huang.redis.UserKey;
import imooc.com.huang.result.CodeMsg;
import imooc.com.huang.result.Result;
import imooc.com.huang.service.UserService;

@Controller
@RequestMapping("/demo")
public class DemoController {
	//此部分作用 1.rest api json输出 2 页面
	
	@Autowired
	UserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	MQSender sender;
	@RequestMapping("/")
    @ResponseBody
    String Hello() {
    	 return "Hello World!";
    }
	@RequestMapping("/hello")
	@ResponseBody
	public Result<String> home() {
		return Result.success("hello imooc");
	}
	
//	@RequestMapping("/mq")
//	@ResponseBody
//	public Result<String> mq() {
//		sender.send("hello rabbit");
//		return Result.success("hello Mr.Huang");
//	}
//	@RequestMapping("/mq/topic")
//	@ResponseBody
//	public Result<String> mqTopic() {
//		sender.sendTopic("hello topic rabbit");
//		return Result.success("hello Mr.Huang");
//	}
//	
//	
//	@RequestMapping("/mq/fanout")
//	@ResponseBody
//	public Result<String> mqFanout() {
//		sender.sendFanout("hello fanout rabbit");
//		return Result.success("hello Mr.Huang");
//	}
//	
//	@RequestMapping("/mq/header")
//	@ResponseBody
//	public Result<String> mqHeader() {
//		sender.sendHeader("hello header rabbit");
//		return Result.success("hello Mr.Huang");
//	}
	
	@RequestMapping("/helloerror")
	@ResponseBody
	public Result<String> homeerror() {
		return Result.error(CodeMsg.SERVER_ERROR);
	}
	@RequestMapping("/thymeleaf")
	public String thymeleaf(Model model) {
		model.addAttribute("name", "huang");
		return "hello";
	}
	@RequestMapping("/db/get")
	@ResponseBody
	public Result<User> dbGet(){
		User user = userService.getById(1);
		return Result.success(user);
	}
	@RequestMapping("/db/tx")
	@ResponseBody
	public Result<Boolean> dbTx(){      
		boolean result = userService.tx();
		return Result.success(result);
	}
	@RequestMapping("/redis/get")
	@ResponseBody
	public Result<User> RedisGet(){
		//redisService.set("key1", "sdasda");
		User user = redisService.get(UserKey.getId,""+1, User.class);
		return Result.success(user);
	}
	@RequestMapping("/redis/set")
	@ResponseBody
	public Result<Boolean>  RedisSet(){
    	User user  = new User();
    	user.setId(1);
    	user.setName("1111");
		redisService.set(UserKey.getId,""+1, user);
		//String v1 = redisService.get(UserKey.getId,"key1", String.class);
		return Result.success(true);
	}
}
