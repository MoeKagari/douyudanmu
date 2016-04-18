package douyudanmu.room;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import douyudanmu.tool.Message;
import douyudanmu.tool.ZhiboStart;

/**
 * һ���򵥵Ľ���
 * @author MoeKagari
 *
 */
public class DanmuRoom{
	private static int count = 0; //������¼���ٸ������Ѿ��ر�
	
	private ZhiboStart zhiboStart;
	
	private JFrame frame;
	private JTextPane text;
	private JScrollPane jsc;
	private Document doc;
	private SimpleAttributeSet set;
	
	public DanmuRoom(ZhiboStart zhiboStart){
		this.zhiboStart = zhiboStart;
		initFrame();
	}
	private void initFrame(){
		this.frame = new JFrame();
		frame.setSize(450,600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				zhiboStart.finish();
				count++;
				if(count == zhiboStart.getNumber())//������н��涼�ѹرգ����˳�����
					System.exit(0);
			}
		});
		
		text = new JTextPane();
		text.setEditable(false);
		text.setFont(new Font("TimesRoman",Font.PLAIN,18));
		
		jsc = new JScrollPane(text);
		frame.getContentPane().add(jsc);
		
		doc = text.getDocument();
		set = new SimpleAttributeSet();
	}
	
	public void show(){
		frame.setTitle(
				(zhiboStart.getOnline()?"[����]":"[����]") +
				zhiboStart.getRoomTitle()
				);
		frame.setVisible(true);
	}
	public void printError(String message){
		try {
			frame.setVisible(true);
			
			StyleConstants.setForeground(set,Color.red);
			StyleConstants.setBold(set, false);
			doc.insertString(doc.getLength(), message + "\n", set);
			
			text.setCaretPosition(text.getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	public void printMessage(String message){
		try {
			StyleConstants.setForeground(set,Color.black);
			StyleConstants.setBold(set, false);
			doc.insertString(doc.getLength(), message + "\n", set);
			
			text.setCaretPosition(text.getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	public void printDanmu(Message danmu){
		try {
			StyleConstants.setForeground(set,Color.blue);
			StyleConstants.setBold(set, true);
			doc.insertString(doc.getLength(), danmu.getUsername(), set);
			
			StyleConstants.setForeground(set,Color.black);
			StyleConstants.setBold(set, false);
			doc.insertString(doc.getLength(),"��" + danmu.getWord() + "\n", set);
			
			text.setCaretPosition(text.getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	public void reTitle(String title,boolean online){
		frame.setTitle(
				(zhiboStart.getOnline()?"[����]":"[����]") +
				zhiboStart.getRoomTitle()
				);
	}
	
}
