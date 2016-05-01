package douyudanmu.room;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import douyudanmu.ZhiboStart;
import douyudanmu.tool.HtmlPage;

/**
 * 用来初始化zhiboServer和upInfo
 * @author MoeKagari
 *
 */
public class ZhiboRoom {
	private static final String path = "http://www.douyu.com/";
	
	private boolean initIsSuccessful;
	
	private UPInfomation upInfo;
	private ArrayList<MyServer> zhiboServer;
	
	private ZhiboStart zhiboStart;
	
	public ZhiboRoom(ZhiboStart zhiboStart) {
		this.zhiboStart = zhiboStart;
		initIsSuccessful = init();
		if(!initIsSuccessful)
			printMessage("获取房间源代码失败，或许是房间不存在，或许网络有问题：\n"
						+ path + zhiboStart.getTag());
		else
			printMessage("当前主播" + (upInfo.getOnline()?"在线":"离线"));
	}
	private boolean init() {
		String code = HtmlPage.getHtmlContent(path + zhiboStart.getTag());
		if(code == null)
			return false;
		
		this.zhiboServer = parseZhiboServer(code);
		this.upInfo = new UPInfomation(code);
		
		return true;
	}
	private void printMessage(String message){
		zhiboStart.printMessage(message);
	}
	
	
	
	
	
	
	
	
	
	
	public boolean getInitIsSuccessful(){
		return initIsSuccessful;
	}
	public ArrayList<MyServer> getZhiboServer(){
		return zhiboServer;
	}
	public boolean getOnline(){
		return upInfo.getOnline();
	}
	public String getRoom_name() {
		return upInfo.getRoom_name();
	}
	public String getRoom_url() {
		return upInfo.getRoom_url();
	}
	public int getRoom_id() {
		return upInfo.getRoom_id();
	}
	public String[] getAvatar(){
		return upInfo.getAvatar();
	}
	
	/*----------------------------------------------*/
	private final static String[] regex_zhiboServer = {
			"\"server_config\":\"(.+?)\"",
			"%7B%22ip%22%3A%22(.+?)%22%2C%22port%22%3A%22(.+?)%22%7D"
			};
	private static ArrayList<MyServer> parseZhiboServer(String code){
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
	private static Matcher getMatcher(String code,String regex){
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(code);
		return mat;
	}
}
