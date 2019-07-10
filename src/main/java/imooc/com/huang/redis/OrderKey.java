package imooc.com.huang.redis;

public class OrderKey extends BasePrefix{

	private OrderKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
		// TODO Auto-generated constructor stub
	}
	private OrderKey(String prefix) {
		super(prefix);
	}
    public static OrderKey getId = new OrderKey("OrderId");
    public static OrderKey getName = new OrderKey("OrderName");	
    public static OrderKey getMiaoShaOrderByUidGid = new OrderKey("getMiaoShaOrderByUidGid");	

}
