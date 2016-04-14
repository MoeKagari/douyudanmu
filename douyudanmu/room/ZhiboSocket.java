package douyudanmu.room;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import douyudanmu.thread.ReceiveThread;
import douyudanmu.thread.SendThread;
import douyudanmu.tool.Mode;
import douyudanmu.tool.Parse;
import douyudanmu.tool.ZhiboStart;

/**
 * 用来初始化danmuServer,gid,rid
 * @author MoeKagari
 *
 */
public class ZhiboSocket implements MySocket {
	private final Mode info = Mode.zhibo;
	private ZhiboStart zhiboStart;
	
	private ArrayList<DanmuServer> danmuServer;
	private int gid;
	private int rid;
	
	private boolean connectIsSuccessful;
	
	private Socket socket;
	private ReceiveThread receiver;
	private SendThread sender;
	
	public ZhiboSocket(ZhiboStart zhiboStart){
		this.zhiboStart = zhiboStart;
		connect();//连接直播服务器是否成功，成功就登陆
		if(connectIsSuccessful)
			login();//登陆直播服务器，之后初始化danmuServer,gid,rid
	}
	private void connect(){
		try {
			socket = new Socket(zhiboStart.getZhiboAdress(),zhiboStart.getZhiboPort());
			receiver = new ReceiveThread(socket.getInputStream(),info,zhiboStart);
			sender = new SendThread(socket.getOutputStream(),info,zhiboStart);
			connectIsSuccessful = true;
		} catch (IOException e) {
			print("连接直播服务器失败......");
			connectIsSuccessful = false;
		}
	}
	private void login(){
		print("向直播服务器发送登陆请求......");
		sender.sendLoginMessage(zhiboStart.getRoomId());
		String message = null;
		message = receiver.receive();
		print("收到直播服务器返回的第一次包.");
		message = receiver.receive();
		print("收到直播服务器返回的第二次包.");
		initGid(message);
		initRid(message);
		initDanmuServer(message);
	}
	private void initGid(String data){
		setGid(Parse.parseGid(data));
	}
	private void initRid(String data){
		setRid(Parse.parseRid(data));
	}
	private void initDanmuServer(String data){
		setDanmuServer(Parse.parseDanmuServer(data));
	}
	private void print(String message){
		zhiboStart.printMessage(message);
	}
	

	
	public void run(){
		receiver.start();
		sender.start();
	}
	public void finish(){
		receiver.finish();
		sender.finish();
	}
	public void close(){
		try {
			receiver.close();
			sender.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public ArrayList<DanmuServer> getDanmuServer(){
		return danmuServer;
	}
	private void setDanmuServer(ArrayList<DanmuServer> danmuServer){
		this.danmuServer = danmuServer;
	}
	public int getGid(){
		return gid;
	}
	private void setGid(int gid){
		this.gid = gid;
	}
	public int getRid(){
		return rid;
	}
	private void setRid(int rid){
		this.rid = rid;
	}
	public boolean getConnectIsSuccessful(){
		return connectIsSuccessful;
	}
}
