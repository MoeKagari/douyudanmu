package douyudanmu.room;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import douyudanmu.tool.Parse;

/**
 * 存放UP主的信息
 * @author MoeKagari
 *
 */
public class UPInfomation {
	private int room_id;
	private String room_name;
	private String[] avatar = new String[3];
	private int owner_uid;
	private String owner_name;
	private int show_status;
	private String room_url;
	
	public UPInfomation(String code) {
		this.room_id = parse_room_id(code);
		this.room_name = parse_room_name(code);
		this.avatar = parse_avatar(code);
		this.owner_uid = parse_owner_uid(code);
		this.owner_name = parse_owner_name(code);
		this.show_status = parse_show_status(code);
		this.room_url = parse_room_url(code);
	}
	
	
	
	public int getRoom_id() {
		return room_id;
	}
	public String getRoom_name() {
		return room_name;
	}
	public String[] getAvatar() {
		return avatar;
	}
	public int getOwner_uid() {
		return owner_uid;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public boolean getOnline() {
		return show_status == 1;
	}
	public String getRoom_url() {
		return room_url;
	}














	/*------------------------------------------------------*/
	private final static String regex_room_id = "\"room_id\":(.+?),";
	private final static String regex_room_name = "\"room_name\":\"(.+?)\"";
	private final static String regex_avatar = ""
			+ "\"avatar\":\\{"
			+ "\"big\":\"(.+?)\","
			+ "\"middle\":\"(.+?)\","
			+ "\"small\":\"(.+?)\"\\}";
	private final static String regex_owner_uid = "\"owner_uid\":(.+?),";
	private final static String regex_owner_name = "\"owner_name\":\"(.+?)\"";
	private final static String regex_show_status = "\"show_status\":(.+?),";
	private final static String regex_room_url = "\"room_url\":\"(.+?)\"";
	
	private static Matcher getMatcher(String code,String regex){
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(code);
		return mat;
	}
	private static int parse_room_id(String code){
		int res = 0;
		Matcher mat = getMatcher(code, regex_room_id);
		if(mat.find()){
			res = Integer.parseInt(mat.group(1));
		}
		return res;
	}
	private static String parse_room_name(String code){
		String res = null;
		Matcher mat = getMatcher(code, regex_room_name);
		if(mat.find()){
			res = mat.group(1);
		}
		return Parse.unicodeToString(res);
	}
	private static String[] parse_avatar(String code){
		String[] res = new String[3];
		Matcher mat = getMatcher(code, regex_avatar);
		if(mat.find()){
			res[0] = mat.group(1).replace("\\","");
			res[1] = mat.group(2).replace("\\","");
			res[2] = mat.group(3).replace("\\","");
		}
		return res;
	}
	private static int parse_owner_uid(String code){
		int res = 0;
		Matcher mat = getMatcher(code, regex_owner_uid);
		if(mat.find()){
			res = Integer.parseInt(mat.group(1));
		}
		return res;
	}
	private static String parse_owner_name(String code){
		String res = null;
		Matcher mat = getMatcher(code, regex_owner_name);
		if(mat.find()){
			res = mat.group(1);
		}
		return Parse.unicodeToString(res);
	}
	private static int parse_show_status(String code){
		int res = 0;
		Matcher mat = getMatcher(code, regex_show_status);
		if(mat.find()){
			res = Integer.parseInt(mat.group(1));
		}
		return res;
	}
	private static String parse_room_url(String code){
		String res = null;
		Matcher mat = getMatcher(code, regex_room_url);
		if(mat.find()){
			res = mat.group(1).replace("\\","");;
		}
		return res;
	}
}
