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
			socket = new Socket(zhiboStart.getDanmuAdress(),zhiboStart.getDanmuPort());
			receiver = new ReceiveThread(socket.getInputStream(),info,zhiboStart);
			sender = new SendThread(socket.getOutputStream(),info,zhiboStart);
			keeper = new KeepLiveThread(socket.getOutputStream(),zhiboStart);
			connectIsSuccessful = true;
		} catch (IOException e) {
			print("���ӵ�Ļ������ʧ��......");
			connectIsSuccessful = false;
		}
	}
	private void login(){
		print("��Ļ���������͵�½����......");
		sender.sendLoginMessage(zhiboStart.getRoomId());
		receiver.receive();
		print("�յ���Ļ���������صİ�");
		print("��Ļ���������ͼ���group������......");
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
		waitKeepFinish();//�ȴ�keeper���̽�����֮��ſ�ʼ�رո�����
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
	 * �ȴ�keeper����
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
