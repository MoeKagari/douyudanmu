package douyudanmu.room;
import java.io.IOException;
import java.net.Socket;

import douyudanmu.thread.KeepLiveThread;
import douyudanmu.thread.ReceiveThread;
import douyudanmu.thread.SendThread;
import douyudanmu.tool.Mode;
import douyudanmu.tool.ZhiboStart;

/**
 * ��½��Ļ��������֮��ʼ���ܵ�Ļ����
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
		init();
	}
	private void init(){
		printMessage("��ʼ���ӵ�Ļ������......");
		connectIsSuccessful = connect();//���ӵ�Ļ�������Ƿ�ɹ����ɹ��͵�½
		if(!connectIsSuccessful){
			printError("���ӵ�Ļ������ʧ��......");
			printMessage("�����Զ��ر�,�����йرս���......");
		}
		printMessage("���ӵ�Ļ�������ɹ�......");
		
		printMessage("��Ļ���������͵�½����......");
		loginIsSuccessful = login();//��½��Ļ������
		if(!loginIsSuccessful){
			printError("��½��Ļ������ʧ��");
			printMessage("�����Զ��ر�,�����йرս���......");
		}
		printMessage("��½��Ļ�������ɹ�");
		printMessage("��ʼ���յ�Ļ");
		printMessage("-------------------------------------");
	}
	private boolean connect(){
		try {
			socket = new Socket(zhiboStart.getDanmuAdress(),zhiboStart.getDanmuPort());
			receiver = new ReceiveThread(socket.getInputStream(),info,zhiboStart);
			sender = new SendThread(socket.getOutputStream(),info,zhiboStart);
			keeper = new KeepLiveThread(socket.getOutputStream(),zhiboStart);
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
		
		if(!sender.sendJoinGroupMessage(zhiboStart.getRid(),zhiboStart.getGid()))
			return false;
		
		return true;
	}
	private void printMessage(String message){
		zhiboStart.printMessage(message);
	}
	private void printError(String error){
		zhiboStart.printError(error);
	}
	
	
	public void start(){
		if(sender != null)
			sender.start();
		if(receiver != null)
			receiver.start();
		if(keeper != null)
			keeper.start();
	}
	public void finish(){
		if(sender != null)
			sender.finish();
		if(receiver != null)
			receiver.finish();
		if(keeper != null)
			keeper.finish();
		waitKeeperFinish();//�ȴ�keeper�߳̽���
	}
	public void close(){
		try {
			if(receiver != null)
				receiver.close();
			if(sender != null)
				sender.close();
			if(keeper != null)
				keeper.close();
			if(socket != null)
				socket.close();
		} catch (IOException e) {
			System.err.println("DanmuSocket.close()����");
		}
	}
	
	/**
	 * �ȴ�keeper����
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
	public boolean getLoginIsSuccessful(){
		return loginIsSuccessful;
	}
}
