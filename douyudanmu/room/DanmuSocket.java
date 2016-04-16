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
	private boolean loginIsSuccessful;
	
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
			print("开始连接弹幕服务器......");
			socket = new Socket(zhiboStart.getDanmuAdress(),zhiboStart.getDanmuPort());
			receiver = new ReceiveThread(socket.getInputStream(),info,zhiboStart);
			sender = new SendThread(socket.getOutputStream(),info,zhiboStart);
			keeper = new KeepLiveThread(socket.getOutputStream(),zhiboStart);
			setConnectIsSuccessful(true);
			print("连接弹幕服务器成功......");
		} catch (IOException e) {
			setConnectIsSuccessful(false);
			print("连接弹幕服务器失败......");
		}
	}
	private void login(){
		print("向弹幕服务器发送登陆请求......");
		if(!sender.sendLoginMessage(zhiboStart.getRoomId())){
			setLoginIsSuccessful(false);
			return;
		}
		
		String message = receiver.receive();
		if(message == null){
			setLoginIsSuccessful(false);
			return;
		}
		print("收到弹幕服务器返回的包");
		
		print("向弹幕服务器发送加入group的请求......");
		if(!sender.sendJoinGroupMessage(zhiboStart.getRid(),zhiboStart.getGid())){
			setLoginIsSuccessful(false);
			return;
		}
		
		print("登陆弹幕服务器成功");
		print("开始接收弹幕");
		print("-------------------------------------");
		setLoginIsSuccessful(true);
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
		waitKeeperFinish();//等待keeper线程结束
	}
	public void close(){
		try {
			receiver.close();
			sender.close();
			keeper.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("DanmuSocket.close()错误");
		}
	}
	
	/**
	 * 等待keeper结束
	 */
	private void waitKeeperFinish(){
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
	private void setConnectIsSuccessful(boolean connectIsSuccessful){
		this.connectIsSuccessful = connectIsSuccessful;
	}
	public boolean getLoginIsSuccessful(){
		return loginIsSuccessful;
	}
	private void setLoginIsSuccessful(boolean loginIsSuccessful){
		this.loginIsSuccessful = loginIsSuccessful;
	}
}
