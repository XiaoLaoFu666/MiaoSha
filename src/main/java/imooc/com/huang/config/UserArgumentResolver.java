package imooc.com.huang.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import imooc.com.huang.domain.MiaoShaUser;
import imooc.com.huang.service.MiaoShaUserService;
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	MiaoShaUserService miaoShaUserService;
	
	public boolean supportsParameter(MethodParameter parameter) {
		// TODO Auto-generated method stub
		
		Class<?> clazz = parameter.getParameterType();
		return clazz == MiaoShaUser.class;
	}

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		String paramToken = request.getParameter(MiaoShaUserService.COOKIE_NAME_TOKEN);
		String cookieToken = getCookieValue(request,MiaoShaUserService.COOKIE_NAME_TOKEN);
		if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token =StringUtils.isEmpty(paramToken)?cookieToken:paramToken; 
		return miaoShaUserService.getByToken(response, token);
	}
	
	private String getCookieValue(HttpServletRequest request,String cookieName) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return null;
		}
		for(Cookie cookie:cookies) {
			if(cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
