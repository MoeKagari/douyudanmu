package douyudanmu.room;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import douyudanmu.MainStart;
import douyudanmu.tool.ZhiboStart;

/**
 * 一个简单的界面
 * @author MoeKagari
 *
 */
public class DanmuRoom{
	static int count = 0; //用来记录多少个界面已经关闭
	
	private ZhiboStart zhiboStart;
	
	private JFrame frame;
	JTextArea textarea;
	JScrollPane jsc;
	
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
				if(count == zhiboStart.getNumber())//如果所有界面都已关闭，就退出进程
					System.exit(0);
			}
		});
		
		textarea = new JTextArea("");
		textarea.setEditable(false);
		textarea.setFont(new Font("TimesRoman",Font.PLAIN,18));
		textarea.setLineWrap(true);
		
		jsc = new JScrollPane(textarea);
		frame.getContentPane().add(jsc);
	}
	
	public void show(){
		frame.setTitle(
				(zhiboStart.getOnline()?"[在线]":"[离线]") +
				zhiboStart.getRoomTitle()
				);
		frame.setVisible(true);
	}
	
	public void printMessage(String message){
		textarea.append(message + "\n");
		textarea.setCaretPosition(textarea.getText().length());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		String[] name = {
				"457896"
		};//房间名字，上网页看到的地址栏最后
		int zhiboxianlu = 0;//直播线路，0开始，不能过大，过大则0
		int danmuxianlu = 0;//弹幕线路，0开始，不能过大，过大则0
		new MainStart(name,zhiboxianlu,danmuxianlu);
		
	}
	
}
