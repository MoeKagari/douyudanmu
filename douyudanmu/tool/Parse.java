package douyudanmu.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 转换数据类
 * @author MoeKagari
 *
 */
public class Parse {
	
	/**
	 * int数组转换为String，结果非字面值，而是编码对应
	 */
	public static String intArrayToString(int[] arg){
		StringBuffer res = new StringBuffer("");
		for(int i = 0;i < arg.length;i++){
			res.append((char)arg[i]);
		}
		return res.toString();
	}
	/**
	 * 将字符串中的“右斜杠uxxxx”格式转为对应的unicode字符
	 */
	public static String unicodeToString(String code){
		String res = new String(code);
		Matcher mat = getMatcher(res,"\\\\u.{4,4}");
		while(mat.find()){
			String old = mat.group();
			char ch = unicodeToChar(old);
			res = res.replace(old,ch+"");
		}
		return res;
	}
	/**
	 * “右斜杠uxxxx”格式的字符串转换为unicode字符
	 */
	private static char unicodeToChar(String unicode){
		long res = 0;
		for(int i = 2;i < unicode.length();i++){
			boolean isOtherChar = false;
			long ch = 0;
			switch(unicode.charAt(i)){
				case '1':ch =  1;break;
				case '2':ch =  2;break;
				case '3':ch =  3;break;
				case '4':ch =  4;break;
				case '5':ch =  5;break;
				case '6':ch =  6;break;
				case '7':ch =  7;break;
				case '8':ch =  8;break;
				case '9':ch =  9;break;
				case '0':ch =  0;break;
				case 'a':
				case 'A':ch = 10;break;
				case 'b':
				case 'B':ch = 11;break;
				case 'c':
				case 'C':ch = 12;break;
				case 'd':
				case 'D':ch = 13;break;
				case 'e':
				case 'E':ch = 14;break;
				case 'f':
				case 'F':ch = 15;break;
				default :isOtherChar = true;
			}
			if(isOtherChar){
				System.out.println("unicode含有其他字符.");
				return 0;
			}
			res += ch << (5 - i) * 4;
		}
		
		return (char)res;
	}
	public static byte[] getByteArray(String data){
		byte[] res = new byte[data.length()];
		for(int i = 0;i < data.length();i++)
			res[i] = (byte)data.charAt(i);
		return res;
	}
	private static Matcher getMatcher(String code,String regex){
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(code);
		return mat;
	}
	
	
	
	
}
