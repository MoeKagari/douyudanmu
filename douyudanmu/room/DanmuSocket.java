package douyudanmu.room;
import java.io.IOException;
import java.net.Socket;

import douyudanmu.thread.KeepLiveThread;
import douyudanmu.thread.ReceiveThread;
import douyudanmu.thread.SendThread;
import douyudanmu.tool.Mode;
import douyudanmu.tool.ZhiboStart;
/**
 * 登陆弹幕服务器，之后开始接受弹幕进程
 * @author MoeKagari
 *
 */
public class DanmuSocket implements MySocket {
	private final Mode info = Mode.danmu;
	private ZhiboStart zhiboStart;
	
	private boolean connectIsSuccessful;
	
	private Socket socket;
	private ReceiveThread receiver;
	private SendThread sender;
	private KeepLiveThread keeper;
	
	public DanmuSocket(ZhiboStart zhiboStart) {
		this.zhiboStart = zhiboStart;
		connect();//连接弹幕服务器是否成功，成功就登陆
		if(connectIsSuccessful)
			login();//登陆弹幕服务器
	}
	private void connect(){
		try {
			socket = new Socket(zhiboStart.getDanmuAdress(),zhiboStart.getDanmuPort());
			receiver = new ReceiveThread(socket.getInputStream(),info,zhiboStart);
			sender = new SendThread(socket.getOutputStream(),info,zhiboStart);
			keeper = new KeepLiveThread(socket.getOutputStream(),zhiboStart);
			connectIsSuccessful = true;
		} catch (IOException e) {
			print("连接弹幕服务器失败......");
			connectIsSuccessful = false;
		}
	}
	private void login(){
		print("向弹幕服务器发送登陆请求......");
		sender.sendLoginMessage(zhiboStart.getRoomId());
		receiver.receive();
		print("收到弹幕服务器返回的包");
		print("向弹幕服务器发送加入group的请求......");
		sender.sendJoinGroupMessage(zhiboStart.getRid(),zhiboStart.getGid());
	}
	private void print(String message){
		zhiboStart.printMessage(message);
	}
	
	
	public void run(){
		sender.start();
		receiver.start();
		keeper.start();
	}
	public void finish(){
		receiver.finish();
		keeper.finish();
		waitKeepFinish();//等待keeper进程结束，之后才开始关闭各个流
	}
	public void close(){
		try {
			receiver.close();
			sender.close();
			keeper.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 等待keeper结束
	 */
	private void waitKeepFinish(){
		while(keeper.isOver()){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public boolean getConnectIsSuccessful(){
		return connectIsSuccessful;
	}
}
