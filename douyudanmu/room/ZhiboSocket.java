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
		printMessage("��ʼ����ֱ��������......");
		connectIsSuccessful = connect();//����ֱ���������Ƿ�ɹ����ɹ��͵�½
		if(!connectIsSuccessful){
			printError("����ֱ��������ʧ��");
			printMessage("�����Զ��ر�,�����йرս���......");
		}
		printMessage("����ֱ���������ɹ�");
		
		printMessage("��ֱ�����������͵�½����......");
		loginIsSuccessful = login();//��½ֱ����������֮���ʼ��danmuServer,gid,rid
		if(!loginIsSuccessful){
			printError("��½ֱ��������ʧ��");
			printMessage("�����Զ��ر�,�����йرս���......");
		}
		printMessage("��½ֱ���������ɹ�");
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
			System.err.println("ZhiboSocket.close()����");
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
