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
 * ������ʼ��danmuServer,gid,rid
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
	private boolean loginIsSuccessful;
	
	private Socket socket;
	private ReceiveThread receiver;
	private SendThread sender;
	
	public ZhiboSocket(ZhiboStart zhiboStart){
		this.zhiboStart = zhiboStart;
		connect();//����ֱ���������Ƿ�ɹ����ɹ��͵�½
		if(connectIsSuccessful)
			login();//��½ֱ����������֮���ʼ��danmuServer,gid,rid
	}
	private void connect(){
		try {
			print("��ʼ����ֱ��������......");
			socket = new Socket(zhiboStart.getZhiboAdress(),zhiboStart.getZhiboPort());
			receiver = new ReceiveThread(socket.getInputStream(),info,zhiboStart);
			sender = new SendThread(socket.getOutputStream(),info,zhiboStart);
			setConnectIsSuccessful(true);
			print("����ֱ���������ɹ�......");
		} catch (IOException e) {
			setConnectIsSuccessful(false);
			print("����ֱ��������ʧ��......");
		}
	}
	private void login(){
		print("��ֱ�����������͵�½����......");
		if(!sender.sendLoginMessage(zhiboStart.getRoomId())){
			setLoginIsSuccessful(false);
			return;
		}
		
		String message = null;
		message = receiver.receive();
		if(message == null){
			setLoginIsSuccessful(false);
			return;
		}
		print("�յ�ֱ�����������صĵ�һ�ΰ�.");
		
		message = receiver.receive();
		if(message == null){
			setLoginIsSuccessful(false);
			return;
		}
		print("�յ�ֱ�����������صĵڶ��ΰ�.");
		
		initGid(message);
		initRid(message);
		initDanmuServer(message);
		print("��½ֱ���������ɹ�");
		setLoginIsSuccessful(true);
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
			System.err.println("ZhiboSocket��close()����");
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
