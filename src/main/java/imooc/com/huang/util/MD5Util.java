package imooc.com.huang.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cglib.proxy.Dispatcher;

public class MD5Util {
	 //明文转码
     public static String md5(String src) {
    	 return DigestUtils.md5Hex(src);
     }
      
     public static String salt = "1a2b3c4d";
     //客户端第一次转码
     public static String inputPassToFromPass(String inputPass) {
    	 String src = "" + salt.charAt(0)+ salt.charAt(2)+inputPass+ salt.charAt(5)+ salt.charAt(4);
    	 return md5(src);
     }
     //服务端转码
     public static String fromPassToDBPass(String fromPass,String salt) {
    	 String src = "" + salt.charAt(0)+ salt.charAt(2)+fromPass+ salt.charAt(5)+ salt.charAt(4);
    	 return md5(src);
     }
     
     public static String inputPassToDBPass(String inputPass,String saltDB) {
    	 String fromPass = inputPassToFromPass(inputPass);
    	 String DBPass = fromPassToDBPass(fromPass, saltDB);
    	 return DBPass;
     }
     
     public static void main(String arg[]) {
    	 //System.out.println(inputPassToFromPass("123456"));
         //System.out.println(fromPassToDBPass(inputPassToFromPass("123456"), salt));
         System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));
     }
}
