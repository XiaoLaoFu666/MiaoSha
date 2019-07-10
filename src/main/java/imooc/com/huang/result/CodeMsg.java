package imooc.com.huang.result;

public class CodeMsg {
  	

	private int code;
    private String msg;
    
    //通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0,"sucess");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
	public static Object BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");;

    //登陆模块
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500201,"密码不能为空！");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500202,"手机号不能为空！");
    public static CodeMsg MOBILE_EXITS = new CodeMsg(500203,"手机号不存在！");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500204,"手机号码格式错误！");   
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500205,"密码输入错误");
	public static CodeMsg MOBILE_HAS= new CodeMsg(500206,"该账号已注册");
	public static CodeMsg SESSION_ERROR = new CodeMsg(500207,"session已失效");
	//商品模块
    //订单模块
	public static  CodeMsg ORDER_NOT_EXITS =  new CodeMsg(500400,"订单不存在");
    
    //秒杀模块
	public static CodeMsg MIAOSHA_OVER= new CodeMsg(500501,"秒杀已经结束");
	public static CodeMsg REPEATE_MIAOSHA= new CodeMsg(500502,"不能重复秒杀");
    private CodeMsg(int code, String msg) {
		// TODO Auto-generated constructor stub
         this.code = code;
         this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}

}
