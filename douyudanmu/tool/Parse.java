package douyudanmu.tool;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import douyudanmu.room.DanmuServer;
import douyudanmu.room.ZhiboServer;

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
	public static char unicodeToString(String unicode){
		int ch = 0;
		for(int i = 2;i < unicode.length();i++){
			boolean isOtherChar = false;
			switch(unicode.charAt(i)){
				case '1':ch +=  1 << (5 - i) * 4;break;
				case '2':ch +=  2 << (5 - i) * 4;break;
				case '3':ch +=  3 << (5 - i) * 4;break;
				case '4':ch +=  4 << (5 - i) * 4;break;
				case '5':ch +=  5 << (5 - i) * 4;break;
				case '6':ch +=  6 << (5 - i) * 4;break;
				case '7':ch +=  7 << (5 - i) * 4;break;
				case '8':ch +=  8 << (5 - i) * 4;break;
				case '9':ch +=  9 << (5 - i) * 4;break;
				case '0':ch +=  0 << (5 - i) * 4;break;
				case 'a':
				case 'A':ch += 10 << (5 - i) * 4;break;
				case 'b':
				case 'B':ch += 11 << (5 - i) * 4;break;
				case 'c':
				case 'C':ch += 12 << (5 - i) * 4;break;
				case 'd':
				case 'D':ch += 13 << (5 - i) * 4;break;
				case 'e':
				case 'E':ch += 14 << (5 - i) * 4;break;
				case 'f':
				case 'F':ch += 15 << (5 - i) * 4;break;
				default :isOtherChar = true;
			}
			if(isOtherChar){
				System.out.println("unicode含有其他字符.");
				return 0;
			}
		}
		
		return (char)ch;
	}
	
	public static boolean parseOnline(String code){
		Matcher mat = getMatcher(code,regex_online);
		int status = 0;
		if(mat.find()){
			status = Integer.parseInt(mat.group(1));
		}
		return status == 1;
			
	}
	public static int parseRid(String data){
		int rid = -1;
		Matcher mat = getMatcher(data, regex_rid);
		if(mat.find()){
			rid = Integer.parseInt(mat.group(1));
		}
		return rid;
	}
	public static int parseGid(String data){
		int gid = -1;
		Matcher mat = getMatcher(data, regex_gid);
		if(mat.find()){
			gid = Integer.parseInt(mat.group(1));
		}
		return gid;
	}
	public static int parseId(String code){
		int id = -1;
		Matcher mat = getMatcher(code, regex_room_id);
		if(mat.find()){
			id = Integer.parseInt(mat.group(1));
		}
		return id;
	}
	public static String parseName(String code){
		String name = null;
		Matcher mat = getMatcher(code,regex_room_name[0]);
		if(mat.find()){
			name = mat.group(1);
		}
		mat = getMatcher(name,regex_room_name[1]);
		while(mat.find()){
			String unicode = mat.group();
			char ch = unicodeToString(unicode);
			name = name.replace(unicode,new Character(ch).toString());
		}
		return name;
	}
	public static ArrayList<ZhiboServer> parseZhiboServer(String code){
		ArrayList<ZhiboServer> res = new ArrayList<ZhiboServer>();
		Matcher mat = getMatcher(code, regex_zhiboServer[0]);
		if(mat.find()){
			String temp = mat.group(1);
			mat = getMatcher(temp, regex_zhiboServer[1]);
			while(mat.find()){
				String adress = mat.group(1);
				int port = Integer.parseInt(mat.group(2));
				res.add(new ZhiboServer(adress, port));
			}
		}
		return res;
	}
	public static ArrayList<DanmuServer> parseDanmuServer(String code){
		ArrayList<DanmuServer> danmuServer = new ArrayList<DanmuServer>();
		Matcher mat = getMatcher(code,regex_danmuServer);
		while(mat.find()){
			String adress = mat.group(1);
			int port = Integer.parseInt(mat.group(2));
			danmuServer.add(new DanmuServer(adress,port));
		}
		return danmuServer;
	}
	public static byte[] getByteArray(String data){
		byte[] b = new byte[data.length()];
		for(int i = 0;i < data.length();i++)
			b[i] = (byte)data.charAt(i);
		return b;
	}
}
