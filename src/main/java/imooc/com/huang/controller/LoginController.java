package imooc.com.huang.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import imooc.com.huang.co.LoginVo;
import imooc.com.huang.result.CodeMsg;
import imooc.com.huang.result.Result;
import imooc.com.huang.service.MiaoShaUserService;
import imooc.com.huang.util.ValidatorUtil;

@Controller
@RequestMapping("/login")
public class LoginController {
 
	@Autowired
	MiaoShaUserService miaoShaUserService;
	private static org.slf4j.Logger log = LoggerFactory.getLogger(LoginController.class);
	@RequestMapping("/to_login")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/do_login")
	@ResponseBody
	public Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo loginVo){
		 log.info(loginVo.toString());
		 String inputMobile = loginVo.getMobile();
		 String inputpass  = loginVo.getPassword();
		 //参数校验
//		 if(StringUtils.isEmpty(inputMobile)) {
//			 return Result.error(CodeMsg.MOBILE_EMPTY);
//		 }
//		 if(StringUtils.isEmpty(inputpass)) {
//			 return Result.error(CodeMsg.PASSWORD_EMPTY);
//		 }
//		 if(!ValidatorUtil.isMobile(inputMobile)) {
//			 return Result.error(CodeMsg.MOBILE_ERROR);
//		 }
		 //登录
		 CodeMsg cm = miaoShaUserService.login(response,loginVo);
         if(cm.getCode()==0) {
        	 return Result.success(true);
         }else {
        	 return Result.error(cm);
         }
	}
	
	@RequestMapping("/to_signUp")
	public String toSignUp() {
		return "signUp";
	}
	
}
