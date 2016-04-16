package douyudanmu.thread;

import java.io.IOException;
import java.io.OutputStream;

import douyudanmu.tool.Conmunication;
import douyudanmu.tool.Mode;
import douyudanmu.tool.Parse;
import douyudanmu.tool.ZhiboStart;

public class SendThread extends Thread implements MyThread {
	private OutputStream os;
	private Mode info;
	//private ZhiboStart zhiboStart;

	public SendThread(OutputStream os,Mode info,ZhiboStart zhiboStart) {
		this.os = os;
		this.info = info;
		//this.zhiboStart = zhiboStart;
	}
	
	public boolean sendLoginMessage(int roomId){
		String message = "";
		if(info == Mode.danmu)
			message = Conmunication.getLoginMessageForDanmuRoute(roomId);
		else if(info == Mode.zhibo)
			message = Conmunication.getLoginMessageForZhiboRoute(roomId);
		return send(message);
	}
	public boolean sendJoinGroupMessage(int rid,int gid){
		if(info == Mode.danmu){
			String message = Conmunication.getJoinGroupMessage(rid,gid);
			return send(message);
		}
		return false;
	}
	public boolean send(String message){
		try {
			byte[] b = Parse.getByteArray(message);
			os.write(b);
			return true;
		} catch (IOException e) {
			return false;
		}
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
