package douyudanmu.tool;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import douyudanmu.room.MyServer;

public class Parse {
	private final static String regex_room_id = "\"room_id\":(.+?),";
	private final static String[] regex_room_name = {
			"\"room_name\":\"(.+?)\"",
			"\\\\u.{4,4}"
	};
	private final static String[] regex_zhiboServer = {
			"\"server_config\":\"(.+?)\"",
			"%7B%22ip%22%3A%22(.+?)%22%2C%22port%22%3A%22(.+?)%22%7D"
			};
	private final static String regex_online = "\"show_status\":(.+?),";
	private final static String regex_danmuServer = 
			"@ASnr@AA=1@ASml@AA=10000@ASip@AA=(.+?)@ASport@AA=(.+?)@AS";
	private final static String regex_gid = "/gid@=(.+?)/";
	private final static String regex_rid = "/rid@=(.+?)/";
	
	
	private static Matcher getMatcher(String code,String regex){
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(code);
		return mat;
	}
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
	 * “右斜杠uxxxx”格式的字符串转换为unicode字符
	 */
	private static char unicodeToString(String unicode){
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
	
	public static boolean parseOnline(String code){
		Matcher mat = getMatcher(code,regex_online);
		int res = 0;
		if(mat.find()){
			res = Integer.parseInt(mat.group(1));
		}
		return res == 1;
			
	}
	public static int parseRid(String data){
		int res = -1;
		Matcher mat = getMatcher(data, regex_rid);
		if(mat.find()){
			res = Integer.parseInt(mat.group(1));
		}
		return res;
	}
	public static int parseGid(String data){
		int res = -1;
		Matcher mat = getMatcher(data, regex_gid);
		if(mat.find()){
			res = Integer.parseInt(mat.group(1));
		}
		return res;
	}
	public static int parseId(String code){
		int res = -1;
		Matcher mat = getMatcher(code, regex_room_id);
		if(mat.find()){
			res = Integer.parseInt(mat.group(1));
		}
		return res;
	}
	public static String parseRoomTitle(String code){
		String res = null;
		Matcher mat = getMatcher(code,regex_room_name[0]);
		if(mat.find()){
			res = mat.group(1);
		}
		mat = getMatcher(res,regex_room_name[1]);
		while(mat.find()){
			String unicode = mat.group();
			char ch = unicodeToString(unicode);
			res = res.replace(unicode,new Character(ch).toString());
		}
		return res;
	}
	public static ArrayList<MyServer> parseZhiboServer(String code){
		ArrayList<MyServer> res = new ArrayList<MyServer>();
		Matcher mat = getMatcher(code, regex_zhiboServer[0]);
		if(mat.find()){
			String temp = mat.group(1);
			mat = getMatcher(temp, regex_zhiboServer[1]);
			while(mat.find()){
				String adress = mat.group(1);
				int port = Integer.parseInt(mat.group(2));
				res.add(new MyServer(adress, port));
			}
		}
		return res;
	}
	public static ArrayList<MyServer> parseDanmuServer(String code){
		ArrayList<MyServer> res = new ArrayList<MyServer>();
		Matcher mat = getMatcher(code,regex_danmuServer);
		while(mat.find()){
			String adress = mat.group(1);
			int port = Integer.parseInt(mat.group(2));
			res.add(new MyServer(adress,port));
		}
		return res;
	}
	public static byte[] getByteArray(String data){
		byte[] res = new byte[data.length()];
		for(int i = 0;i < data.length();i++)
			res[i] = (byte)data.charAt(i);
		return res;
	}
}
