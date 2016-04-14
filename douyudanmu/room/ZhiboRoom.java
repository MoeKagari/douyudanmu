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
	private int roomId;
	private String roomTitle;
	private ArrayList<ZhiboServer> zhiboServer;
	private boolean online;
	
	private ZhiboStart zhiboStart;
	
	public ZhiboRoom(ZhiboStart zhiboStart) {
		this.zhiboStart = zhiboStart;
		init();
	}
	private void init() {
		String code = HtmlPage.getHtmlContent(zhiboStart.getRoomUrl());
		this.roomId = Parse.parseId(code);
		this.roomTitle = Parse.parseName(code);
		this.zhiboServer = Parse.parseZhiboServer(code);
		this.online = Parse.parseOnline(code);
		zhiboStart.printMessage("当前主播" + (getOnline()?"在线":"离线"));
	}
	
	
	
	
	public boolean getOnline(){
		return online;
	}
	public String getRoomTitle(){
		return roomTitle;
	}
	public int getRoomId(){
		return roomId;
	}
	public ArrayList<ZhiboServer> getZhiboServer() {
		return zhiboServer;
	}
}
