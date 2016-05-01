package douyudanmu.thread;

import java.io.IOException;
import java.io.InputStream;

import douyudanmu.ZhiboStart;
import douyudanmu.tool.Message;

public class ReceiveThread extends Thread {
	private boolean over = false;

	private InputStream is;
	private ZhiboStart zhiboStart;

	public ReceiveThread(InputStream is, ZhiboStart zhiboStart) {
		this.is = is;
		this.zhiboStart = zhiboStart;
	}

	public String receiveMessage() {
		try {
			byte[] b = new byte[8 * 1024];
			is.read(b);

			String data = new String(b, "utf8");
			return data;
		} catch (IOException e) {
			return null;
		}
	}

	public String receive() {
		try {
			byte[] b = new byte[8 * 1024];
			is.read(b);

			String data = new String(b, "utf8");
			Message message = new Message(data);
			zhiboStart.printDanmu(message);
			if ("chatmsg".equals(message.getType()))
				return message.toString();
			else
				return data;
		} catch (IOException e) {
			return null;
		}
	}

	public void run() {
		while (!over) {
			receive();
		}
	}

	public void finish() {
		over = true;
	}
}
