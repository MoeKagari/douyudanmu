package douyudanmu.thread;

import java.io.IOException;
import java.io.OutputStream;

import douyudanmu.tool.Log;
import douyudanmu.tool.Conmunication;
import douyudanmu.tool.Mode;
import douyudanmu.tool.Parse;
import douyudanmu.tool.ZhiboStart;

public class SendThread extends Thread implements MyThread {
	private OutputStream os;
	private Mode info;
	private ZhiboStart zhiboStart;

	public SendThread(OutputStream os,Mode info,ZhiboStart zhiboStart) {
		this.os = os;
		this.info = info;
		this.zhiboStart = zhiboStart;
	}
	
	public void sendLoginMessage(int roomId){
		String message = "";
		if(info == Mode.danmu)
			message = Conmunication.getLoginMessageForDanmuRoute(roomId);
		else if(info == Mode.zhibo)
			message = Conmunication.getLoginMessageForZhiboRoute(roomId);
		send(message);
	}
	public void sendJoinGroupMessage(int rid,int gid){
		if(info == Mode.danmu){
			String message = Conmunication.getJoinGroupMessage(rid,gid);
			send(message);
		}
	}
	public void send(String message){
		try {
			byte[] b = Parse.getByteArray(message);
			os.write(b);
			//printMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void printMessage(String message){
		if(info == Mode.zhibo)
			Log.messageToZhiboServer(message);
		else if(info == Mode.danmu)
			Log.messageToDanmuServer(message);
	}
	
	
	
	public void run(){}
	public void finish(){}
	public void close(){
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
