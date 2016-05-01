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
 * ���Ƶ�Ļ��JPanel
 * @author MoeKagari
 *
 */
public class UIPanel extends JPanel {
	private Config config = Config.getInstance();

	// Ĭ������
	private Font defaultFont;
	// Ĭ��������ɫ
	private Color defaultColor;
	// �ı�
	private ArrayList<String> text;
	//�������Ϣ����,����resize֮����ʾ
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
		int blockX = config.getBlockX();// �ı��������ҿհ�
		int blockY = config.getBlockY();// �ı��������¿հ�
		int lineSpacing = config.getLineSpacing();// �ı������м��
		int wordSpacing = config.getWordSpacing();// �ı������ּ��

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
