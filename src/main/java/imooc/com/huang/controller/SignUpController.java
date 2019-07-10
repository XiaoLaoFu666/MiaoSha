package imooc.com.huang.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import imooc.com.huang.co.LoginVo;
import imooc.com.huang.co.SignUp;
import imooc.com.huang.co.SignUpVo;
import imooc.com.huang.result.CodeMsg;
import imooc.com.huang.result.Result;
import imooc.com.huang.service.MiaoShaUserService;
import imooc.com.huang.util.ValidatorUtil;

@Controller
@RequestMapping("/SignUp")
public class SignUpController {

	@Autowired
	MiaoShaUserService miaoShaUserService;
	private static org.slf4j.Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("/do_signUp")
	@ResponseBody
	public Result<Boolean> doLogin(@Valid SignUpVo signUpVo){
		 log.info(signUpVo.toString());
		 String inputMobile = signUpVo.getMobile();
		 String inputpass  = signUpVo.getPassword();
		 String inputname = signUpVo.getName();
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
		 CodeMsg cm = miaoShaUserService.signUp(signUpVo);
         if(cm.getCode()==0) {
        	 return Result.success(true);
         }else {
        	 return Result.error(cm);
         }
	}
}
