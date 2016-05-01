package douyudanmu.tool;

import java.awt.Color;
import java.awt.Font;

/**
 * �����࣬��ʱ��֧�ִ��ļ�����͵������ļ�
 * @author MoeKagari
 *
 */
public class Config {
	private int frameWidth = 400;
	private int frameHeight = 200;
	private int frameLocationX = 400;
	private int frameLocationY = 300;
	private boolean onTop = true;
	private Color background = new Color(0, 0, 0, 0.6f);
	private boolean resizable = false;

	public int getFrameWidth() {
		return frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public int getFrameLocationX() {
		return frameLocationX;
	}

	public int getFrameLocationY() {
		return frameLocationY;
	}

	public boolean isOnTop() {
		return onTop;
	}

	public Color getBackground() {
		return background;
	}

	public boolean isResizable() {
		return resizable;
	}

	private int blockX = 10;// �ı��������ҿհ�
	private int blockY = 4; // �ı��������¿հ�
	private int lineSpacing = 1;// �ı������м��
	private int wordSpacing = 0;// �ı������ּ��
	private Font UIPanelFont = new Font("΢���ź�", Font.PLAIN, 16);
	private Color UIPanelTextColor = Color.WHITE;

	public int getBlockX() {
		return blockX;
	}

	public int getBlockY() {
		return blockY;
	}

	public int getLineSpacing() {
		return lineSpacing;
	}

	public int getWordSpacing() {
		return wordSpacing;
	}

	public Font getUIPanelFont() {
		return UIPanelFont;
	}

	public Color getUIPanelTextColor() {
		return UIPanelTextColor;
	}

	/**
	 * ����ģʽ
	 */
	public static Config getInstance() {
		if (config == null) {
			config = new Config();
		}
		return config;
	}

	private static Config config;
}
