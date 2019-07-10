package imooc.com.huang.redis;

public class MiaoShaKey extends BasePrefix{

	private MiaoShaKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
		// TODO Auto-generated constructor stub
	}
	private MiaoShaKey(String prefix) {
		super(prefix);
	}
    public static MiaoShaKey isGoodsOver = new MiaoShaKey("goodsOver");
}
