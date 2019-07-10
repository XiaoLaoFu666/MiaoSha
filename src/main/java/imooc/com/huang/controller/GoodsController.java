package imooc.com.huang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import imooc.com.huang.co.GoodsDetailvo;
import imooc.com.huang.co.GoodsVo;
import imooc.com.huang.domain.Goods;
import imooc.com.huang.domain.MiaoShaUser;
import imooc.com.huang.redis.GoodsKey;
import imooc.com.huang.redis.RedisService;
import imooc.com.huang.result.Result;
import imooc.com.huang.service.GoodsService;
import imooc.com.huang.service.MiaoShaUserService;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	RedisService redisService;
	
	@Autowired
	MiaoShaUserService miaoShaUserService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	ThymeleafViewResolver thymeleafViewResolver; 
	
	@Autowired
	org.springframework.context.ApplicationContext applicationContext;
	
//	@RequestMapping("/to_list")
//	public String toList(HttpServletResponse response,Model model,
//			@CookieValue (value = MiaoShaUserService.COOKIE_NAME_TOKEN,required = false)String cookieToken,
//			@RequestParam(value = MiaoShaUserService.COOKIE_NAME_TOKEN,required = false)String paramToken) {
//		if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)) {
//			return "login";
//		}
//		//paramToken 优先级高，先从里面取
//		String token =StringUtils.isEmpty(paramToken)?cookieToken:paramToken; 
//		MiaoShaUser user = miaoShaUserService.getByToken(response,token);
//		model.addAttribute("user", user);
//		return "goods_list";
//	}
	
	
	
	@RequestMapping("/to_allgoodslist")
	public String allList(HttpServletRequest request,HttpServletResponse response,Model model,MiaoShaUser user) {
		model.addAttribute("user", user);
		List<Goods> allgoodslist = goodsService.getAllGoods();
		model.addAttribute("allgoods",allgoodslist);

		return "allgoods_list";
	}
		
	
	
    @RequestMapping(value = "/to_miaoshalist",produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request,HttpServletResponse response,Model model,MiaoShaUser user) {
    	
    	//取缓存
    	String html = redisService.get(GoodsKey.GoodsListKey, "", String.class);
    	if(!StringUtils.isEmpty(html)) {
    		return html;
    	}
    	model.addAttribute("user", user);
    	List<GoodsVo> goodsList = goodsService.getMiaoShaoGoods();
    	model.addAttribute("goodsList",goodsList);

    	//手动渲染
		SpringWebContext ctx= new SpringWebContext(request, response, request.getServletContext(),
				request.getLocale(), model.asMap(), applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if(!StringUtils.isEmpty(html)) {
        	redisService.set(GoodsKey.GoodsListKey,"", html);
        }
        return html;
    }
    
    // 商品详情页显示
    @RequestMapping(value = "/to_detail2/{goodsId}",produces = "text/html")
    @ResponseBody
    public String todetail2(HttpServletRequest request,HttpServletResponse response,
    		Model model,MiaoShaUser user,@PathVariable("goodsId") long goodsId) {
    	model.addAttribute("user", user);
    	//取缓存
    	String html = redisService.get(GoodsKey.GoodsDetailKey, ""+goodsId, String.class);
    	if(!StringUtils.isEmpty(html)) {
    		return html;
    	}
    	
        GoodsVo goods = goodsService.getValueById(goodsId);
        model.addAttribute("goods", goods);
        long MiaoShaStart = goods.getStartDate().getTime();
        long MiaioShaEnd  = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoShaStatus = 0;  //当前状态
        int remainSeconds = 0;  // 剩余时间
        if(now<MiaoShaStart) {//秒杀还没开始
        	miaoShaStatus = 0;
        	remainSeconds = (int)((MiaoShaStart - now)/1000);
       }else if(now>MiaioShaEnd) {//秒杀已经结束
    	    remainSeconds = -1;
    	    miaoShaStatus = 2;
       }else {//秒杀正在进行中
    	    miaoShaStatus = 1;
    	    remainSeconds = 0;
       }
        model.addAttribute("miaoShaStatus", miaoShaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
    	//手动渲染
		SpringWebContext ctx= new SpringWebContext(request, response, request.getServletContext(),
				request.getLocale(), model.asMap(), applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("goods_list_detail", ctx);
        if(!StringUtils.isEmpty(html)) {
        	redisService.set(GoodsKey.GoodsListKey,"", html);
        }
        return html;

    }
    
    
    //静态化商品详情页面
    @RequestMapping(value = "/to_detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailvo> todetail(HttpServletRequest request,HttpServletResponse response,
    		Model model,MiaoShaUser user,@PathVariable("goodsId") long goodsId) {   	  	
        GoodsVo goods = goodsService.getValueById(goodsId);
       // model.addAttribute("goods", goods);
        long MiaoShaStart = goods.getStartDate().getTime();
        long MiaioShaEnd  = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoShaStatus = 0;  //当前状态
        int remainSeconds = 0;  // 剩余时间
        if(now<MiaoShaStart) {//秒杀还没开始
        	miaoShaStatus = 0;
        	remainSeconds = (int)((MiaoShaStart - now)/1000);
       }else if(now>MiaioShaEnd) {//秒杀已经结束
    	    remainSeconds = -1;
    	    miaoShaStatus = 2;
       }else {//秒杀正在进行中
    	    miaoShaStatus = 1;
    	    remainSeconds = 0;
       }
        //model.addAttribute("miaoShaStatus", miaoShaStatus);
        //model.addAttribute("remainSeconds", remainSeconds);
    	GoodsDetailvo goodsDetailvo = new GoodsDetailvo();
    	goodsDetailvo.setGoods(goods);
    	goodsDetailvo.setMiaoShaStatus(miaoShaStatus);
    	goodsDetailvo.setMiaoShaUser(user);
    	goodsDetailvo.setRemainSeconds(remainSeconds);
        return Result.success(goodsDetailvo);

    }
    
}
