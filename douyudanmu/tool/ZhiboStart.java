package douyudanmu.tool;

import douyudanmu.room.DanmuRoom;
import douyudanmu.room.DanmuSocket;
import douyudanmu.room.MyServer;
import douyudanmu.room.ZhiboRoom;
import douyudanmu.room.ZhiboSocket;
import douyudanmu.thread.ReTitleThread;

public class ZhiboStart {
	private final String path = "http://www.douyu.com/";
	
	private String name;
	private int zhiboxianlu;
	private int danmuxianlu;
	private int number;//记录有多少个界面
	
	private DanmuRoom danmuRoom;
	private ZhiboRoom zhiboRoom;
	private MyServer zhiboRoute;
	private ZhiboSocket zhiboSocket;
	private MyServer danmuRoute;
	private DanmuSocket danmuSocket;
	private ReTitleThread reTitler;
	
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
		if(!zhiboRoom.getInitIsSuccessful())
			return;
		
		show();
		initZhiboRoute();
		if(zhiboRoute == null)
			return;
		
		initZhiboSocket();
		if(!zhiboSocket.getConnectIsSuccessful() || !zhiboSocket.getLoginIsSuccessful())
			return;
		
		initDanmuRoute();
		if(danmuRoute == null)
			return;
		
		initDanmuSocket();
		if(!danmuSocket.getConnectIsSuccessful() || !danmuSocket.getLoginIsSuccessful())
			return;
		
		initReTitler();
		
		start();
	}
	private void initDanmuRoom(){
		danmuRoom = new DanmuRoom(this);
	}
	private void initZhiboRoom(){
		zhiboRoom = new ZhiboRoom(this);
	}
	private void initZhiboRoute(){
		if(zhiboxianlu + 1 > zhiboRoom.getZhiboServer().size())
			zhiboRoute = zhiboRoom.getZhiboServer().get(0);
		else
			zhiboRoute = zhiboRoom.getZhiboServer().get(zhiboxianlu);
	}
	private void initZhiboSocket(){
		zhiboSocket = new ZhiboSocket(this);
	}
	private void initDanmuRoute(){
		if(danmuxianlu + 1 > zhiboSocket.getDanmuServer().size())
			danmuRoute = zhiboSocket.getDanmuServer().get(0);
		else
			danmuRoute = zhiboSocket.getDanmuServer().get(danmuxianlu);
	}
	private void initDanmuSocket(){
		danmuSocket = new DanmuSocket(this);
	}
	private void initReTitler(){
		reTitler = new ReTitleThread(this);
	}
	private void show(){
		danmuRoom.show();
	}
	private void waitReTitlerFinish(){
		while(reTitler.isOver()){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	public void printError(String error){
		danmuRoom.printError(error);
	}
	public void printMessage(String message){
		danmuRoom.printMessage(message);
	}
	public void printDanmu(Message danmu){
		danmuRoom.printDanmu(danmu);
	}
	public void reTitle(String title,boolean online){
		danmuRoom.reTitle(title,online);
	}
	
	
	
	public void start() {
		if(zhiboSocket != null)
			zhiboSocket.start();
		if(danmuSocket != null)
			danmuSocket.start();
		if(reTitler != null)
			reTitler.start();
	}
	public void finish(){
		if(zhiboSocket != null)
			zhiboSocket.finish();
		if(danmuSocket != null)
			danmuSocket.finish();
		if(reTitler != null)
			reTitler.finish();
		waitReTitlerFinish();
		close();
	}
	public void close(){
		if(zhiboSocket != null)
			zhiboSocket.close();
		if(danmuSocket != null)
			danmuSocket.close();
		if(reTitler != null)
			reTitler.close();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int getNumber(){
		return number;
	}
	public boolean getOnline(){
		return zhiboRoom.getOnline();
	}
	public void setOnline(boolean online){
		zhiboRoom.setOnline(online);
	}
	public String getRoomTitle(){
		return zhiboRoom.getRoomTitle();
	}
	public void setRoomTitle(String roomTitle){
		zhiboRoom.setRoomTitle(roomTitle);
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
