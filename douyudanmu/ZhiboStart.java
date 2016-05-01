package douyudanmu;

import java.io.File;

import javax.swing.ImageIcon;

import douyudanmu.room.DanmuRoom;
import douyudanmu.room.DanmuSocket;
import douyudanmu.room.MyServer;
import douyudanmu.room.ZhiboRoom;
import douyudanmu.room.ZhiboSocket;
import douyudanmu.tool.Downloader;
import douyudanmu.tool.Message;

/**
 * Controller，存放各个Model
 * @author MoeKagari
 *
 */
public class ZhiboStart {
	private String tag;
	private int zhiboxianlu;
	private int danmuxianlu;

	private DanmuRoom danmuRoom;
	private ZhiboRoom zhiboRoom;
	private ImageIcon[] avatar;//用来存放big，middle，small这三种头像
	private MyServer zhiboRoute;
	private ZhiboSocket zhiboSocket;
	private MyServer danmuRoute;
	private DanmuSocket danmuSocket;

	public ZhiboStart(String tag, int zhiboxianlu, int danmuxianlu) {
		this.tag = tag;
		this.zhiboxianlu = zhiboxianlu;
		this.danmuxianlu = danmuxianlu;
		init();
	}

	private void init() {
		initDanmuRoom();

		initZhiboRoom();
		if (!zhiboRoom.getInitIsSuccessful())
			return;

		initAvatar();

		danmuRoom.diaplay();

		initZhiboRoute();

		initZhiboSocket();
		if (!zhiboSocket.getConnectIsSuccessful() || !zhiboSocket.getLoginIsSuccessful())
			return;

		initDanmuRoute();

		initDanmuSocket();
		if (!danmuSocket.getConnectIsSuccessful() || !danmuSocket.getLoginIsSuccessful())
			return;

		start();
	}

	private void initDanmuRoom() {
		danmuRoom = new DanmuRoom(this);
	}

	private void initZhiboRoom() {
		zhiboRoom = new ZhiboRoom(this);
	}

	private void initAvatar() {
		avatar = new ImageIcon[3];
		String[] address = zhiboRoom.getAvatar();
		
		File big = new File("avatar\\big.jpg");
		Downloader.downloadToFile(address[0], big);
		avatar[0] = new ImageIcon(big.getAbsolutePath());
		
		File middle = new File("avatar\\middle.jpg");
		Downloader.downloadToFile(address[1], middle);
		avatar[1] = new ImageIcon(middle.getAbsolutePath());
		
		File small = new File("avatar\\small.jpg");
		Downloader.downloadToFile(address[2], small);
		avatar[2] = new ImageIcon(small.getAbsolutePath());
	}

	private void initZhiboRoute() {
		if (zhiboxianlu + 1 > zhiboRoom.getZhiboServer().size())
			zhiboRoute = zhiboRoom.getZhiboServer().get(0);
		else
			zhiboRoute = zhiboRoom.getZhiboServer().get(zhiboxianlu);
	}

	private void initZhiboSocket() {
		zhiboSocket = new ZhiboSocket(this);
	}

	private void initDanmuRoute() {
		if (danmuxianlu + 1 > zhiboSocket.getDanmuServer().size())
			danmuRoute = zhiboSocket.getDanmuServer().get(0);
		else
			danmuRoute = zhiboSocket.getDanmuServer().get(danmuxianlu);
	}

	private void initDanmuSocket() {
		danmuSocket = new DanmuSocket(this);
	}

	public void printMessage(String message) {
		danmuRoom.printMessage(message);
	}

	public void printDanmu(Message danmu) {
		danmuRoom.printDanmu(danmu);
	}

	public void start() {
		if (zhiboSocket != null)
			zhiboSocket.start();
		if (danmuSocket != null)
			danmuSocket.start();
	}

	public void finish() {
		if (zhiboSocket != null)
			zhiboSocket.finish();
		if (danmuSocket != null)
			danmuSocket.finish();
	}

	public String getTag() {
		return tag;
	}

	public boolean getOnline() {
		return zhiboRoom.getOnline();
	}

	public String getRoom_name() {
		return zhiboRoom.getRoom_name();
	}

	public String getRoom_url() {
		return zhiboRoom.getRoom_url();
	}

	public int getRoom_id() {
		return zhiboRoom.getRoom_id();
	}

	/*---------------------------------*/
	public String getZhiboHost() {
		return zhiboRoute.getHost();
	}

	public int getZhiboPort() {
		return zhiboRoute.getPort();
	}

	public String getDanmuHost() {
		return danmuRoute.getHost();
	}

	public int getDanmuPort() {
		return danmuRoute.getPort();
	}

	public int getRid() {
		return zhiboSocket.getRid();
	}

	public int getGid() {
		return zhiboSocket.getGid();
	}

}
