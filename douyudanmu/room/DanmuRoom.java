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
 * һ���򵥵Ľ���
 * @author MoeKagari
 *
 */
public class DanmuRoom{
	static int count = 0; //������¼���ٸ������Ѿ��ر�
	
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
				if(count == zhiboStart.getNumber())//������н��涼�ѹرգ����˳�����
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
				(zhiboStart.getOnline()?"[����]":"[����]") +
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
		};//�������֣�����ҳ�����ĵ�ַ�����
		int zhiboxianlu = 0;//ֱ����·��0��ʼ�����ܹ��󣬹�����0
		int danmuxianlu = 0;//��Ļ��·��0��ʼ�����ܹ��󣬹�����0
		new MainStart(name,zhiboxianlu,danmuxianlu);
		
	}
	
}
