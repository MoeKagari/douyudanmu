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
		connect();//���ӵ�Ļ�������Ƿ�ɹ����ɹ��͵�½
		if(connectIsSuccessful)
			login();//��½��Ļ������
	}
	private void connect(){
		try {
			print("��ʼ���ӵ�Ļ������......");
			socket = new Socket(zhiboStart.getDanmuAdress(),zhiboStart.getDanmuPort());
			receiver = new ReceiveThread(socket.getInputStream(),info,zhiboStart);
			sender = new SendThread(socket.getOutputStream(),info,zhiboStart);
			keeper = new KeepLiveThread(socket.getOutputStream(),zhiboStart);
			setConnectIsSuccessful(true);
			print("���ӵ�Ļ�������ɹ�......");
		} catch (IOException e) {
			setConnectIsSuccessful(false);
			print("���ӵ�Ļ������ʧ��......");
		}
	}
	private void login(){
		print("��Ļ���������͵�½����......");
		if(!sender.sendLoginMessage(zhiboStart.getRoomId())){
			setLoginIsSuccessful(false);
			return;
		}
		
		String message = receiver.receive();
		if(message == null){
			setLoginIsSuccessful(false);
			return;
		}
		print("�յ���Ļ���������صİ�");
		
		print("��Ļ���������ͼ���group������......");
		if(!sender.sendJoinGroupMessage(zhiboStart.getRid(),zhiboStart.getGid())){
			setLoginIsSuccessful(false);
			return;
		}
		
		print("��½��Ļ�������ɹ�");
		print("��ʼ���յ�Ļ");
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
		waitKeeperFinish();//�ȴ�keeper�߳̽���
	}
	public void close(){
		try {
			receiver.close();
			sender.close();
			keeper.close();
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
