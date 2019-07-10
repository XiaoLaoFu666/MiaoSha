package imooc.com.huang.redis;

public class GoodsKey extends BasePrefix{

	private GoodsKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
		// TODO Auto-generated constructor stub
	}
    
	public static GoodsKey GoodsListKey = new GoodsKey(60,"gl");
	public static KeyPrefix GoodsDetailKey = new GoodsKey(60,"gd");
	public static KeyPrefix getMiaoShaGoodsStock = new GoodsKey(0,"gs");
}
