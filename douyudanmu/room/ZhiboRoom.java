package douyudanmu.room;

import java.util.ArrayList;
import douyudanmu.tool.HtmlPage;
import douyudanmu.tool.Parse;
import douyudanmu.tool.ZhiboStart;

/**
 * ������ʼ��zhiboServer
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
			printError("��ȡ����Դ����ʧ�ܣ������Ƿ��䲻���ڣ��������������⣺\n" + zhiboStart.getRoomUrl());
		else
			printMessage("��ǰ����" + (online?"����":"����"));
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
