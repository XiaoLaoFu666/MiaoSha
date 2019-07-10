package imooc.com.huang.redis;

public class UserKey extends BasePrefix{

	private UserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
		// TODO Auto-generated constructor stub
	}
	private UserKey(String prefix) {
		super(prefix);
	}
	public static UserKey getId = new UserKey("id");

	public static UserKey getName = new UserKey("name");
}
