package douyudanmu.tool;

import java.awt.Color;
import java.awt.Font;

/**
 * 配置类，暂时不支持从文件导入和导出到文件
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

	private int blockX = 10;// 文本区域左右空白
	private int blockY = 4; // 文本区域上下空白
	private int lineSpacing = 1;// 文本区域行间距
	private int wordSpacing = 0;// 文本区域字间距
	private Font UIPanelFont = new Font("微软雅黑", Font.PLAIN, 16);
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
	 * 单例模式
	 */
	public static Config getInstance() {
		if (config == null) {
			config = new Config();
		}
		return config;
	}

	private static Config config;
}
