package douyudanmu.room;

import java.util.ArrayList;
import douyudanmu.tool.HtmlPage;
import douyudanmu.tool.Parse;
import douyudanmu.tool.ZhiboStart;

/**
 * 用来初始化zhiboServer
 * @author MoeKagari
 *
 */
public class ZhiboRoom {
	private boolean initIsSuccessful;
	
	private int roomId;
	private String roomTitle;
	private ArrayList<MyServer> zhiboServer;
	private boolean online;
	
	private ZhiboStart zhiboStart;
	
	public ZhiboRoom(ZhiboStart zhiboStart) {
		this.zhiboStart = zhiboStart;
		initIsSuccessful = init();
		if(!initIsSuccessful)
			printError("获取房间源代码失败，或许是房间不存在，或许网络有问题：\n" + zhiboStart.getRoomUrl());
		else
			printMessage("当前主播" + (online?"在线":"离线"));
	}
	private boolean init() {
		String code = HtmlPage.getHtmlContent(zhiboStart.getRoomUrl());
		if(code == null)
			return false;
		
		this.roomId = Parse.parseId(code);
		this.roomTitle = Parse.parseRoomTitle(code);
		this.zhiboServer = Parse.parseZhiboServer(code);
		this.online = Parse.parseOnline(code);
		return true;
	}
	private void printMessage(String error){
		zhiboStart.printMessage(error);
	}
	private void printError(String error){
		zhiboStart.printError(error);
	}
	
	
	
	public boolean getOnline(){
		return online;
	}
	public void setOnline(boolean online){
		this.online = online;
	}
	public String getRoomTitle(){
		return roomTitle;
	}
	public void setRoomTitle(String roomTitle){
		this.roomTitle = roomTitle;
	}
	public int getRoomId(){
		return roomId;
	}
	public ArrayList<MyServer> getZhiboServer() {
		return zhiboServer;
	}
	public boolean getInitIsSuccessful(){
		return initIsSuccessful;
	}
}
