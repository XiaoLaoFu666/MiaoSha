package imooc.com.huang.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import imooc.com.huang.util.ValidatorUtil;

public class isMobileValidator implements ConstraintValidator<isMobile, String>{

	private boolean required =false;
	//初始化
	public void initialize(isMobile constraintAnnotation) {
		// TODO Auto-generated method stub
		required = constraintAnnotation.required();
		
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		if(required) {
			return ValidatorUtil.isMobile(value);
		}else {
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
