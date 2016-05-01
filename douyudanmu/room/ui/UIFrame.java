package douyudanmu.room.ui;

import javax.swing.JFrame;
import douyudanmu.room.ui.UIPanel;
import douyudanmu.tool.Config;
import douyudanmu.tool.Message;

/**
 * 一个透明的界面
 * 
 * @author MoeKagari
 *
 */
@SuppressWarnings("serial")
public class UIFrame extends JFrame {
	private Config config = Config.getInstance();

	private UIPanel pan;

	public UIFrame() {
		initFrame();
	}

	private void initFrame() {
		setUndecorated(true);
		setSize(config.getFrameWidth(), config.getFrameHeight());
		setResizable(config.isResizable());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(config.getFrameLocationX(), config.getFrameLocationY());
		setAlwaysOnTop(config.isOnTop());
		setBackground(config.getBackground());

		pan = new UIPanel();
		setContentPane(pan);
		MousePressAndMoveListener listener = new MousePressAndMoveListener(this);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
	}

	public void printMessage(String message) {
		pan.setText(message);
		System.out.println(message);
		pan.repaint();
		this.repaint();
	}

	public void printDanmu(Message danmu) {
		printMessage(danmu.toString());
	}

}
