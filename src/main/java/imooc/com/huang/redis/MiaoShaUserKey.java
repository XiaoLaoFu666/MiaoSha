package imooc.com.huang.redis;

public class MiaoShaUserKey extends BasePrefix {
	private static final int TOKEN_EXPIRE = 3600*24*2;

	public MiaoShaUserKey( String prefix) {
		super( prefix);
		// TODO Auto-generated constructor stub
	}
	
	public MiaoShaUserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
		// TODO Auto-generated constructor stub
	}
    public static MiaoShaUserKey token = new MiaoShaUserKey(TOKEN_EXPIRE,"tk");
	public static KeyPrefix getById = new MiaoShaUserKey(0,"id");
}
