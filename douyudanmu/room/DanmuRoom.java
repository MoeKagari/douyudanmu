package douyudanmu.room;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import douyudanmu.ZhiboStart;
import douyudanmu.room.ui.UIFrame;
import douyudanmu.tool.Message;

/**
 * π‹¿ÌUI
 * 
 * @author MoeKagari
 *
 */
public class DanmuRoom {
	private ZhiboStart zhiboStart;

	private UIFrame ui;

	public DanmuRoom(ZhiboStart zhiboStart) {
		this.zhiboStart = zhiboStart;
		initUI();
	}

	private void initUI() {
		ui = new UIFrame();
		ui.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				zhiboStart.finish();
				System.exit(0);
			}
		});
	}

	public void diaplay() {
		ui.setTitle(zhiboStart.getRoom_name());
		ui.setVisible(true);
	}

	public void printMessage(String message) {
		ui.printMessage(message);
	}

	public void printDanmu(Message danmu) {
		ui.printDanmu(danmu);
	}

}
