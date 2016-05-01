package douyudanmu.room;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import douyudanmu.ZhiboStart;
import douyudanmu.thread.ReceiveThread;
import douyudanmu.thread.SendThread;

/**
 * 用来初始化danmuServer,gid,rid
 * 
 * @author MoeKagari
 *
 */
public class ZhiboSocket {
	private ZhiboStart zhiboStart;

	private ArrayList<MyServer> danmuServer;
	private int gid;
	private int rid;

	private boolean connectIsSuccessful;
	private boolean loginIsSuccessful;

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private ReceiveThread receiver;
	private SendThread sender;

	public ZhiboSocket(ZhiboStart zhiboStart) {
		this.zhiboStart = zhiboStart;
		init();
	}

	private void init() {
		printMessage("开始连接直播服务器......");
		connectIsSuccessful = connect();// 连接直播服务器是否成功，成功就登陆
		if (!connectIsSuccessful) {
			printMessage("连接直播服务器失败");
			printMessage("程序自动关闭,请自行关闭界面......");
		}
		printMessage("连接直播服务器成功");

		printMessage("向直播服务器发送登陆请求......");
		loginIsSuccessful = login();// 登陆直播服务器，之后初始化danmuServer,gid,rid
		if (!loginIsSuccessful) {
			printMessage("登陆直播服务器失败");
			printMessage("程序自动关闭,请自行关闭界面......");
		}
		printMessage("登陆直播服务器成功");
	}

	private boolean connect() {
		try {
			socket = new Socket(zhiboStart.getZhiboHost(), zhiboStart.getZhiboPort());
			is = socket.getInputStream();
			os = socket.getOutputStream();
			receiver = new ReceiveThread(is, zhiboStart);
			sender = new SendThread(os, zhiboStart);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private boolean login() {
		if (!sender.sendLoginMessageToZhiboRoute(zhiboStart.getRoom_id()))
			return false;

		String message;
		message = receiver.receiveMessage();
		if (message == null)
			return false;

		message = receiver.receiveMessage();
		if (message == null)
			return false;

		gid = parseGid(message);
		rid = parseRid(message);
		danmuServer = parseDanmuServer(message);
		return true;
	}

	private void printMessage(String message) {
		zhiboStart.printMessage(message);
	}

	public void start() {
		if (receiver != null)
			receiver.start();
		if (sender != null)
			sender.start();
	}

	public void finish() {
		if (receiver != null)
			receiver.finish();
		if (sender != null)
			sender.finish();
		close();
	}

	public void close() {
		try {
			is.close();
			os.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("ZhiboSocket.close()错误");
		}
	}

	public ArrayList<MyServer> getDanmuServer() {
		return danmuServer;
	}

	public int getGid() {
		return gid;
	}

	public int getRid() {
		return rid;
	}

	public boolean getConnectIsSuccessful() {
		return connectIsSuccessful;
	}

	public boolean getLoginIsSuccessful() {
		return loginIsSuccessful;
	}
	
	
	
	/*----------------------------------------------------*/
	
	private final static String regex_danmuServer = 
			"@ASnr@AA=1@ASml@AA=10000@ASip@AA=(.+?)@ASport@AA=(.+?)@AS";
	private final static String regex_gid = "/gid@=(.+?)/";
	private final static String regex_rid = "/rid@=(.+?)/";
	
	private static int parseRid(String data){
		int res = -1;
		Matcher mat = getMatcher(data, regex_rid);
		if(mat.find()){
			res = Integer.parseInt(mat.group(1));
		}
		return res;
	}
	private static int parseGid(String data){
		int res = -1;
		Matcher mat = getMatcher(data, regex_gid);
		if(mat.find()){
			res = Integer.parseInt(mat.group(1));
		}
		return res;
	}
	private static ArrayList<MyServer> parseDanmuServer(String code){
		ArrayList<MyServer> res = new ArrayList<MyServer>();
		Matcher mat = getMatcher(code,regex_danmuServer);
		while(mat.find()){
			String adress = mat.group(1);
			int port = Integer.parseInt(mat.group(2));
			res.add(new MyServer(adress,port));
		}
		return res;
	}
	private static Matcher getMatcher(String code,String regex){
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(code);
		return mat;
	}
	
}
