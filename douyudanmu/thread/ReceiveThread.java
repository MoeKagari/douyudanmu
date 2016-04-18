package douyudanmu.thread;

import java.io.IOException;
import java.io.InputStream;

import douyudanmu.tool.Mode;
import douyudanmu.tool.ZhiboStart;
import douyudanmu.tool.Log;
import douyudanmu.tool.Message;

public class ReceiveThread extends Thread implements MyThread {
	private boolean over = false;
	
	private InputStream is;
	private Mode info;
	private ZhiboStart zhiboStart;
	
	public ReceiveThread(InputStream is,Mode info,ZhiboStart zhiboStart) {
		this.is = is;
		this.info = info;
		this.zhiboStart = zhiboStart;
	}
	
	public String receive(){
		try {
			byte[] b = new byte[8*1024];
			is.read(b);
			
			String data = new String(b,"utf8");
			Message message = new Message(data);
			//如果是弹幕消息，就输出弹幕，否则原样输出
			//或者判断其它类型消息
			if("chatmsg".equals(message.getType())){
				zhiboStart.printDanmu(message);
				return message.toString();
			}
			else{
				//print(data);
				return data;
			}
		} catch (IOException e) {
			return null;
		}
	}
	private void print(String message){
		if(info == Mode.danmu)
			Log.messageFromDanmuServer(message);
		else if(info == Mode.zhibo)
			Log.messageFromZhiboServer(message);
	}
	
	
	
	
	public void run(){
		while(!over){
			receive();
		}
	}
	public void finish(){
		setOver(true);
	}
	public void close(){
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void setOver(boolean over){
		this.over = over;
	}
}
