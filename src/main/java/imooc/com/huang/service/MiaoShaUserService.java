package imooc.com.huang.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import imooc.com.huang.co.LoginVo;
import imooc.com.huang.co.SignUp;
import imooc.com.huang.co.SignUpVo;
import imooc.com.huang.dao.MiaoShaUserDao;
import imooc.com.huang.domain.MiaoShaUser;
import imooc.com.huang.exception.GlobalException;
import imooc.com.huang.redis.MiaoShaUserKey;
import imooc.com.huang.redis.RedisService;
import imooc.com.huang.result.CodeMsg;
import imooc.com.huang.util.MD5Util;
import imooc.com.huang.util.UUIDUtil;

@Service
public class MiaoShaUserService {
 
	public static final String COOKIE_NAME_TOKEN = "token";
	@Autowired
	MiaoShaUserDao miaoShaUserDao;
	
	@Autowired
	RedisService redisService;
	
	public MiaoShaUser getById(Long id) {
		//取缓存
		MiaoShaUser user = redisService.get(MiaoShaUserKey.getById, ""+id, MiaoShaUser.class);
		if(user!=null) {
			return user;
		}
		//取数据库
		user = miaoShaUserDao.getById(id);
		if(user!=null) { 
			redisService.set(MiaoShaUserKey.getById, ""+id, user);
		}
		return user;
	}
	
	public boolean updatePassword(String token,long id,String newpassword) {
		//取缓存
		MiaoShaUser user = redisService.get(MiaoShaUserKey.getById, ""+id, MiaoShaUser.class);
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_EXITS);
		}
		//取数据库
		MiaoShaUser updateUser = miaoShaUserDao.getById(id);
		updateUser.setId(id);
		updateUser.setPassword(MD5Util.fromPassToDBPass(newpassword,updateUser.getSalt()));
		miaoShaUserDao.update(updateUser);
		redisService.delete(MiaoShaUserKey.getById,""+id);
		user.setPassword(updateUser.getPassword());
		redisService.set(MiaoShaUserKey.token,token,user);
		return true;
	}
	
	public CodeMsg login( HttpServletResponse response,LoginVo loginVo) {
		// TODO Auto-generated method stub
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String password = loginVo.getPassword();
		MiaoShaUser user = getById(Long.parseLong(mobile));
		if(user == null)
			throw new GlobalException(CodeMsg.MOBILE_EXITS);
        String psw = user.getPassword();
        String saltDB = user.getSalt();
        String realpsw = MD5Util.fromPassToDBPass(password, saltDB);
        if(!realpsw.equals(psw))
        	throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        String token = UUIDUtil.uuid();
        addCookie(response,token,user);
        return CodeMsg.SUCCESS;
		
	}
	
	 //生成cookie
	public void addCookie(HttpServletResponse response,String token,MiaoShaUser user) {       
        redisService.set(MiaoShaUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
		cookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
	}
	
	
	public CodeMsg signUp(SignUpVo signUpVo) {
		// TODO Auto-generated method stub
		if(signUpVo == null) {
			return CodeMsg.SERVER_ERROR;
		}
		String mobile = signUpVo.getMobile();
		if(miaoShaUserDao.getById(Long.parseLong(mobile))!=null) {
			return CodeMsg.MOBILE_HAS;
		}
		Long id = Long.parseLong(mobile);
		System.out.println(id);
		String password = signUpVo.getPassword();
	
		String name = signUpVo.getName();
		String saltP = UUIDUtil.uuid();  //自动生成随机salt字符串
		String putDBpsw = MD5Util.fromPassToDBPass(password, saltP);
		System.out.println(putDBpsw);
		System.out.println(saltP);
		boolean flag =miaoShaUserDao.setById(id, putDBpsw,name, saltP);
		if(flag) {
			return CodeMsg.SUCCESS;
		}else {
			return CodeMsg.SERVER_ERROR;
		}
	}
	public MiaoShaUser getByToken(HttpServletResponse response,String token) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		MiaoShaUser user =  redisService.get(MiaoShaUserKey.token, token, MiaoShaUser.class);
		if(user != null) {
			addCookie(response,token, user);
		}
		return user;
	}
}
