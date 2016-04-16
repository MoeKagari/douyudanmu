package douyudanmu.tool;

import douyudanmu.room.DanmuRoom;
import douyudanmu.room.DanmuServer;
import douyudanmu.room.DanmuSocket;
import douyudanmu.room.ZhiboRoom;
import douyudanmu.room.ZhiboServer;
import douyudanmu.room.ZhiboSocket;

public class ZhiboStart {
	private final String path = "http://www.douyu.com/";
	
	private String name;
	private int zhiboxianlu;
	private int danmuxianlu;
	private int number;//记录有多少个界面
	
	private DanmuRoom danmuRoom;
	private ZhiboRoom zhiboRoom;
	private ZhiboServer zhiboRoute;
	private ZhiboSocket zhiboSocket;
	private DanmuServer danmuRoute;
	private DanmuSocket danmuSocket;
	
	public ZhiboStart(String name,int zhiboxianlu,int danmuxianlu,int number) {
		this.name = name;
		this.zhiboxianlu = zhiboxianlu;
		this.danmuxianlu = danmuxianlu;
		this.number = number;
		init();
	}
	private void init(){
		initDanmuRoom();
		initZhiboRoom();
		if(!zhiboRoom.getInitIsSuccessful()){
			showError("获取房间源代码失败，或许是房间不存在，或许网络有问题：\n" + getRoomUrl());
			return;
		}
		show();
		initZhiboRoute();
		initZhiboSocket();
		if(!zhiboSocket.getConnectIsSuccessful()){
			printMessage("程序自动关闭,请自行关闭界面......");
			return;
		}
		if(!zhiboSocket.getLoginIsSuccessful()){
			printMessage("程序自动关闭,请自行关闭界面......");
			return;
		}
		initDanmuRoute();
		initDanmuSocket();
		if(!danmuSocket.getConnectIsSuccessful() || !danmuSocket.getLoginIsSuccessful()){
			printMessage("程序自动关闭,请自行关闭界面......");
			return;
		}
		run();
	}
	private void initDanmuRoom(){
		danmuRoom = new DanmuRoom(this);
	}
	private void initZhiboRoom(){
		zhiboRoom = new ZhiboRoom(this);
	}
	private void initZhiboRoute(){
		if(zhiboxianlu > zhiboRoom.getZhiboServer().size())
			zhiboRoute = zhiboRoom.getZhiboServer().get(0);
		else
			zhiboRoute = zhiboRoom.getZhiboServer().get(zhiboxianlu);
	}
	private void initZhiboSocket(){
		zhiboSocket = new ZhiboSocket(this);
	}
	private void initDanmuRoute(){
		if(danmuxianlu > zhiboSocket.getDanmuServer().size())
			danmuRoute = zhiboSocket.getDanmuServer().get(0);
		else
			danmuRoute = zhiboSocket.getDanmuServer().get(danmuxianlu);
	}
	private void initDanmuSocket(){
		danmuSocket = new DanmuSocket(this);
	}
	private void show(){
		danmuRoom.show();
	}
	private void showError(String message){
		danmuRoom.showError(message);
	}
	
	
	
	public void printMessage(String message){
		danmuRoom.printMessage(message);
	}
	public void printDanmu(Message danmu){
		danmuRoom.printDanmu(danmu);
	}
	
	
	public void run() {
		if(zhiboSocket != null)
			zhiboSocket.finish();
		danmuSocket.run();
	}
	public void finish(){
		if(zhiboSocket != null)
			zhiboSocket.finish();
		if(danmuSocket != null)
			danmuSocket.finish();
		this.close();
	}
	public void close(){
		if(zhiboSocket != null)
			zhiboSocket.close();
		if(danmuSocket != null)
			danmuSocket.close();
	}
	
	
	public int getNumber(){
		return number;
	}
	public boolean getOnline(){
		return zhiboRoom.getOnline();
	}
	public String getRoomTitle(){
		return zhiboRoom.getRoomTitle();
	}
	public String getRoomUrl(){
		return path + name;
	}
	public int getRoomId(){
		return zhiboRoom.getRoomId();
	}
	public int getRid(){
		return zhiboSocket.getRid();
	}
	public int getGid(){
		return zhiboSocket.getGid();
	}
	public String getZhiboAdress() {
		return zhiboRoute.getAdress();
	}
	public int getZhiboPort() {
		return zhiboRoute.getPort();
	}
	public String getDanmuAdress() {
		return danmuRoute.getAdress();
	}
	public int getDanmuPort() {
		return danmuRoute.getPort();
	}
}
