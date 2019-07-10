package imooc.com.huang.redis;

public interface KeyPrefix {
	
	//有效期
     public int expireSeconds();
     //获取前缀
     public String getPrefix();
}
