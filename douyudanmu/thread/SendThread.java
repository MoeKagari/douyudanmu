package douyudanmu.thread;

import java.io.IOException;
import java.io.OutputStream;

import douyudanmu.ZhiboStart;
import douyudanmu.tool.Conmunication;
import douyudanmu.tool.Parse;

public class SendThread extends Thread {
	private OutputStream os;

	public SendThread(OutputStream os,ZhiboStart zhiboStart) {
		this.os = os;
	}
	
	
	
	public boolean sendLoginMessageToDanmuRoute(int roomId){
		String message = Conmunication.getLoginMessageForDanmuRoute(roomId);
		return send(message);
	}
	public boolean sendLoginMessageToZhiboRoute(int roomId){
		String message = Conmunication.getLoginMessageForZhiboRoute(roomId);;
		return send(message);
	}
	public boolean sendJoinGroupMessage(int rid,int gid){
		String message = Conmunication.getJoinGroupMessage(rid,gid);
		return send(message);
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
	
	public void run(){
		
	}
	public void finish(){
		
	}
}
