/**
 * 
 */
package org.itachi.cms.util;

import java.security.MessageDigest;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author itachi
 *
 */
public class StringUtil {
	private final static String seed = "1234567890AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

	public static int[] strArrToIntArr(String str) {
		String[] numbers = str.split(",");

		int[] ints = new int[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			ints[i] = Integer.parseInt(numbers[i]);
		}
		return ints;
	}
	public static boolean MinSize(String s){
		String[] groupIds =s.split(",");
		return groupIds.length > 0 ? true : false;
	}

	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * @param  s 需要得到长度的字符串
	 * @return int 得到的字符串长度
	 */
	public static int length(String s) {
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	/**
	 * 检验字符串是否超过指定长度
	 *
	 */
	public static boolean isMaxSize(String str,int size){

		if (str == null || length(str) > size) {
			return false;
		}
		return true;
	}

	/**
	 * 检验字符串是否为空
	 *
	 */
	public static boolean isNotNull(String str){
		if (str == null || str.length() <= 0) {
			 return false;
		}
		return true;
	}


	/**
	 * 检验字符串是否为邮箱地址
	 * 
	 * @param string
	 *            被建议的字符串
	 * @return 是否为邮箱
	 */
	public static boolean isEmail(String string) {
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(string);
		return matcher.matches();
	}

	/**
	 * 建议字符串是否为手机号码
	 * 
	 * @param string
	 *            被建议的字符串
	 * @return 是否手机号码
	 */
	public static boolean isMobile(String string) {
		String check = "^(13|15|18)\\d{9}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(string);
		return matcher.matches();
	}


	
	public static String getEncryptedStr(String s) {  
        char hexDigits[]={'2','0','1','5','V','T','H','A','P','P','0','6','A','T','0','1'};     
  
        try {  
            byte[] btInput = s.getBytes();  
            // 获得MD5摘要算法的 MessageDigest 对象  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
            // 使用指定的字节更新摘要  
            mdInst.update(btInput);  
            // 获得密文  
            byte[] md = mdInst.digest();  
            // 把密文转换成十六进制的字符串形式  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {  
                byte byte0 = md[i];  
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                str[k++] = hexDigits[byte0 & 0xf];  
            }  
            return new String(str);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }
	
	/**
	 * md5加密字符串
	 * 
	 * @param string
	 *            要加密的字符串
	 * @param is32bit
	 *            是否为32位加密，还是16位加密
	 * @return 加密后的字符串
	 */
	public final static String MD5(String string, boolean is32bit) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		
		
		try {
			byte[] btInput = string.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}

			if (is32bit) {
				return new String(str);
			} else {
				return new String(str).substring(8, 24);
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * md5加密字符串,32位
	 * 
	 * @param string
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public final static String MD5(String string) {
		return MD5(string, true);
	}

	/**
	 * 生成对应长度的随机码(包括数字，大小写字母)
	 * @param length 随机码长度
	 * @return 随机码
	 */
	public final static String createRandomCode(int length) {
		if (length <= 0) {
			return null;
		}
		// 生成随机类
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < length; i++) {
			String rand = String.valueOf(seed.charAt(random.nextInt(seed.length())));
			sRand += rand;
		}
		return sRand;
	}
	
	/**
	 *  生成对应长度的随机码(只有数字)
	 * @param length 随机码长度
	 * @return 随机码
	 */
	public final static String createRandomNumber(int length) {
		if (length <= 0) {
			return null;
		}
		// 生成随机类
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < length; i++) {
			String rand = String.valueOf(seed.charAt(random.nextInt(10)));
			sRand += rand;
		}
		return sRand;
	}
	


}
