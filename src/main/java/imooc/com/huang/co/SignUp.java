package imooc.com.huang.co;

public class SignUp {
	private String mobile;
	private String password;
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
	@Override
	public String toString() {
		return "SignUp [mobile=" + mobile + ", password=" + password + "]";
	}
}
