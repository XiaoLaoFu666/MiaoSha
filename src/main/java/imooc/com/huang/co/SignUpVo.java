package imooc.com.huang.co;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import imooc.com.huang.validator.isMobile;

public class SignUpVo {
	@NotNull    
	@isMobile
	private String mobile;
	@NotNull    
	@Length(min = 32)
	private String password;
	private String name;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "SignUpVo [mobile=" + mobile + ", password=" + password + ", name=" + name + "]";
	}
	
}
