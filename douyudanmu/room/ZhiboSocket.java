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
	
	private ArrayList<MyServer> danmuServer;
	private int gid;
	private int rid;
	
	private boolean connectIsSuccessful;
	private boolean loginIsSuccessful;
	
	private Socket socket;
	private ReceiveThread receiver;
	private SendThread sender;
	
	public ZhiboSocket(ZhiboStart zhiboStart){
		this.zhiboStart = zhiboStart;
		init();
	}
	private void init(){
		printMessage("开始连接直播服务器......");
		connectIsSuccessful = connect();//连接直播服务器是否成功，成功就登陆
		if(!connectIsSuccessful){
			printError("连接直播服务器失败");
			printMessage("程序自动关闭,请自行关闭界面......");
		}
		printMessage("连接直播服务器成功");
		
		printMessage("向直播服务器发送登陆请求......");
		loginIsSuccessful = login();//登陆直播服务器，之后初始化danmuServer,gid,rid
		if(!loginIsSuccessful){
			printError("登陆直播服务器失败");
			printMessage("程序自动关闭,请自行关闭界面......");
		}
		printMessage("登陆直播服务器成功");
	}
	private boolean connect(){
		try {
			socket = new Socket(zhiboStart.getZhiboAdress(),zhiboStart.getZhiboPort());
			receiver = new ReceiveThread(socket.getInputStream(),info,zhiboStart);
			sender = new SendThread(socket.getOutputStream(),info,zhiboStart);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	private boolean login(){
		if(!sender.sendLoginMessage(zhiboStart.getRoomId()))
			return false;
		
		String message;
		message = receiver.receive();
		if(message == null)
			return false;
		
		message = receiver.receive();
		if(message == null)
			return false;
		
		initGid(message);
		initRid(message);
		initDanmuServer(message);
		return true;
	}
	private void initGid(String data){
		gid = Parse.parseGid(data);
	}
	private void initRid(String data){
		rid = Parse.parseRid(data);
	}
	private void initDanmuServer(String data){
		danmuServer = Parse.parseDanmuServer(data);
	}
	private void printMessage(String message){
		zhiboStart.printMessage(message);
	}
	private void printError(String error){
		zhiboStart.printError(error);
	}

	
	public void start(){
		if(receiver != null)
			receiver.start();
		if(sender != null)
			sender.start();
	}
	public void finish(){
		if(receiver != null)
			receiver.finish();
		if(sender != null)
			sender.finish();
	}
	public void close(){
		try {
			if(sender != null)
				sender.close();
			if(receiver != null)
				receiver.close();
			if(socket != null)
				socket.close();
		} catch (IOException e) {
			System.err.println("ZhiboSocket.close()错误");
		}
	}
	
	
	
	
	public ArrayList<MyServer> getDanmuServer(){
		return danmuServer;
	}
	public int getGid(){
		return gid;
	}
	public int getRid(){
		return rid;
	}
	public boolean getConnectIsSuccessful(){
		return connectIsSuccessful;
	}
	public boolean getLoginIsSuccessful(){
		return loginIsSuccessful;
	}
}
