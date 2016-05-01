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
 * 登陆弹幕服务器，之后开始接受弹幕进程
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
		printMessage("开始连接弹幕服务器......");
		connectIsSuccessful = connect();// 连接弹幕服务器是否成功，成功就登陆
		if (!connectIsSuccessful) {
			printMessage("连接弹幕服务器失败");
			printMessage("程序自动关闭,请自行关闭界面......");
		}
		printMessage("连接弹幕服务器成功");

		printMessage("向弹幕服务器发送登陆请求......");
		loginIsSuccessful = login();// 登陆弹幕服务器
		if (!loginIsSuccessful) {
			printMessage("登陆弹幕服务器失败");
			printMessage("程序自动关闭,请自行关闭界面......");
		}
		printMessage("登陆弹幕服务器成功");
		printMessage("开始接收弹幕");
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
		waitKeeperFinish();// 等待keeper线程结束
		close();
	}

	public void close() {
		try {
			is.close();
			os.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("DanmuSocket.close()错误");
		}
	}

	/**
	 * 等待keeper结束
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
