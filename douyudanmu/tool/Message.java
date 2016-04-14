package douyudanmu.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
	private String type;
	private String username;
	private String danmu;
	
	public Message(String data) {
		this.type = parseType(data);
		this.username = parseUsername(data);
		this.danmu = parseDanmu(data);
	}
	
	public String toString(){
		if(getType().equals("chatmsg"))
			return username + "��" + danmu;
		return "�ǵ�Ļ��Ϣ.";
	}
	public String getType(){
		return type;
	}
	
	
	/*------------������ȡ�����ķ���------------------------*/
	
	private final static String regex_type = "type@=(.+?)/";
	private final static String regex_username = "nn@=(.+?)/";
	private final static String regex_danmu = "txt@=(.+?)/";
	
	private static Matcher getMatcher(String data,String regex){
		Pattern pat = Pattern.compile(regex);
		return pat.matcher(data);
	}
	private static String parse(String data,String regex){
		Matcher mat = getMatcher(data,regex);
		if(mat.find())
			return mat.group(1);
		return null;
	}
	private static String parseType(String data){
		return parse(data, regex_type);
	}
	private static String parseUsername(String data){
		return parse(data, regex_username);
	}
	private static String parseDanmu(String data){
		return parse(data, regex_danmu);
	}
	
}
