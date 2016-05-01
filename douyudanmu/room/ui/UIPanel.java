package douyudanmu.room.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import douyudanmu.tool.Config;

@SuppressWarnings("serial")
/**
 * 绘制弹幕的JPanel
 * @author MoeKagari
 *
 */
public class UIPanel extends JPanel {
	private Config config = Config.getInstance();

	// 默认字体
	private Font defaultFont;
	// 默认文字颜色
	private Color defaultColor;
	// 文本
	private ArrayList<String> text;
	//缓存的消息数量,用于resize之后显示
	private int limit = 20;

	public UIPanel() {
		defaultFont = config.getUIPanelFont();
		defaultColor = config.getUIPanelTextColor();
		text = new ArrayList<String>();
	}

	public void setText(String message) {
		if(text.size() == limit)
			text.remove(0);
		text.add(message);
	}

	public void paint(Graphics g) {
		this.drawText(g);
	}

	private void drawText(Graphics g) {
		int blockX = config.getBlockX();// 文本区域左右空白
		int blockY = config.getBlockY();// 文本区域上下空白
		int lineSpacing = config.getLineSpacing();// 文本区域行间距
		int wordSpacing = config.getWordSpacing();// 文本区域字间距

		FontMetrics fm = this.getFontMetrics(defaultFont);
		int x = blockX;
		int y = blockY + fm.getAscent();
		int charWidth;
		int charHeight = fm.getHeight();

		int startIndex;
		startIndex = (int) Math.floor((this.getHeight() + lineSpacing - 2 * blockY) * 1.0 / (charHeight + lineSpacing));
		if (text.size() > startIndex)
			startIndex = text.size() - startIndex;
		else
			startIndex = 0;
		
		for (int i = startIndex; i < text.size(); i++) {
			String str = text.get(i);
			for (int index = 0; index < str.length(); index++) {
				char ch = str.charAt(index);
				charWidth = fm.charWidth(ch);
				if (x + charWidth + blockX > this.getWidth()) {
					x = blockX;
					y += charHeight + lineSpacing;
				}
				drawChar(g, ch, x, y);
				x += charWidth + wordSpacing;
			}
			x = blockX;
			y += charHeight + lineSpacing;
		}
	}

	private void drawChar(Graphics g, char ch, int x, int y) {
		g.setColor(defaultColor);
		g.setFont(defaultFont);
		g.drawString("" + ch, x, y);
	}
}
