package douyudanmu.room;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import douyudanmu.ZhiboStart;
import douyudanmu.thread.KeepLiveThread;
import douyudanmu.thread.ReceiveThread;
import douyudanmu.thread.SendThread;

/**
 * ��½��Ļ��������֮��ʼ���ܵ�Ļ����
 * 
 * @author MoeKagari
 *
 */
public class DanmuSocket {
	private ZhiboStart zhiboStart;

	private boolean connectIsSuccessful;
	private boolean loginIsSuccessful;

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private ReceiveThread receiver;
	private SendThread sender;
	private KeepLiveThread keeper;

	public DanmuSocket(ZhiboStart zhiboStart) {
		this.zhiboStart = zhiboStart;
		init();
	}

	private void init() {
		printMessage("��ʼ���ӵ�Ļ������......");
		connectIsSuccessful = connect();// ���ӵ�Ļ�������Ƿ�ɹ����ɹ��͵�½
		if (!connectIsSuccessful) {
			printMessage("���ӵ�Ļ������ʧ��");
			printMessage("�����Զ��ر�,�����йرս���......");
		}
		printMessage("���ӵ�Ļ�������ɹ�");

		printMessage("��Ļ���������͵�½����......");
		loginIsSuccessful = login();// ��½��Ļ������
		if (!loginIsSuccessful) {
			printMessage("��½��Ļ������ʧ��");
			printMessage("�����Զ��ر�,�����йرս���......");
		}
		printMessage("��½��Ļ�������ɹ�");
		printMessage("��ʼ���յ�Ļ");
		printMessage("-------------------------------------");
	}

	private boolean connect() {
		try {
			socket = new Socket(zhiboStart.getDanmuHost(), zhiboStart.getDanmuPort());
			is = socket.getInputStream();
			os = socket.getOutputStream();
			receiver = new ReceiveThread(is, zhiboStart);
			sender = new SendThread(os, zhiboStart);
			keeper = new KeepLiveThread(socket.getOutputStream(), zhiboStart);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private boolean login() {
		if (!sender.sendLoginMessageToDanmuRoute(zhiboStart.getRoom_id()))
			return false;

		String message;
		message = receiver.receiveMessage();
		if (message == null)
			return false;

		if (!sender.sendJoinGroupMessage(zhiboStart.getRid(), zhiboStart.getGid()))
			return false;

		return true;
	}

	private void printMessage(String message) {
		zhiboStart.printMessage(message);
	}

	public void start() {
		if (sender != null)
			sender.start();
		if (receiver != null)
			receiver.start();
		if (keeper != null)
			keeper.start();
	}

	public void finish() {
		if (sender != null)
			sender.finish();
		if (receiver != null)
			receiver.finish();
		if (keeper != null)
			keeper.finish();
		waitKeeperFinish();// �ȴ�keeper�߳̽���
		close();
	}

	public void close() {
		try {
			is.close();
			os.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("DanmuSocket.close()����");
		}
	}

	/**
	 * �ȴ�keeper����
	 */
	private void waitKeeperFinish() {
		while (keeper.isOver()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean getConnectIsSuccessful() {
		return connectIsSuccessful;
	}

	public boolean getLoginIsSuccessful() {
		return loginIsSuccessful;
	}
}
