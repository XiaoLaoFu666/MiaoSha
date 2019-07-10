package imooc.com.huang.exception;

import imooc.com.huang.result.CodeMsg;

public class GlobalException extends RuntimeException{

	//序列化版本号
	private static final long serialVersionUID = 1L;
	
	private CodeMsg cm;
	
	public GlobalException(CodeMsg cm) {
		super(cm.toString());
		this.cm = cm;
	}
	
	public CodeMsg getCm() {
		return cm;
	}
}
