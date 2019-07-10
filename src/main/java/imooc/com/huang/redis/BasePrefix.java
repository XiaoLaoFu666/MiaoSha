package imooc.com.huang.redis;

public abstract class BasePrefix implements KeyPrefix {

	private int expireSeconds;
	private String prefix;
	
	public BasePrefix(String prefix){
		this.expireSeconds = 0;
		this.prefix = prefix;
	} 
	public BasePrefix(int expireSeconds,String prefix){
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}
	public int expireSeconds(){
		// TODO Auto-generated method stub
		//默认0代表永不过期
		return expireSeconds;
	}

	public String getPrefix() {
		// TODO Auto-generated method stub
		String classname = this.getClass().getSimpleName();
		return classname+":"+prefix;
	}

}

