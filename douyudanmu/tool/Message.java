package douyudanmu.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
	private String type;
	private String username;
	private String word;
	
	public Message(String data) {
		this.type = parseType(data);
		this.username = parseUsername(data);
		this.word = parseWord(data);
	}
	
	public String toString(){
		if(getType().equals("chatmsg"))
			return username + "：" + word;
		return "非弹幕消息.";
	}
	public String getType(){
		return type;
	}
	public String getUsername(){
		return username;
	}
	public String getWord(){
		return word;
	}
	
	
	/*------------以下提取参数的方法------------------------*/
	
	private final static String regex_type = "type@=(.+?)/";
	private final static String regex_username = "nn@=(.+?)/";
	private final static String regex_word = "txt@=(.+?)/";
	
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
	private static String parseWord(String data){
		return parse(data, regex_word);
	}
	
}
