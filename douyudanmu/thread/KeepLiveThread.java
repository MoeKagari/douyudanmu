package douyudanmu.thread;

import java.io.IOException;
import java.io.OutputStream;

import douyudanmu.tool.Log;
import douyudanmu.tool.Conmunication;
import douyudanmu.tool.Parse;
import douyudanmu.tool.ZhiboStart;

public class KeepLiveThread extends Thread implements MyThread {
	private OutputStream os;
	private ZhiboStart zhiboStart;
	
	private boolean over = false;
	private boolean sendOver = false;
	
	public KeepLiveThread(OutputStream os,ZhiboStart zhiboStart) {
		this.os = os;
		this.zhiboStart = zhiboStart;
	}
	
	
	private void send(){
		try {
			long total = 40000;
			long per = 5000;
			double count = total/per;
			while(count > 0){
				Thread.sleep(5000);//ÿ5����һ��over
				count--;
				if(over){//���over��������sendOver=true��������
					setSendOver(true);
					return;
				}
			}
			Thread.sleep((long)(count * per));
			//ÿ40�뷢��һ��keeplive��
			String message = Conmunication.getKeepLiveMessage();
			byte[] b = Parse.getByteArray(message);
			os.write(b);Log.keepLive(zhiboStart);
			
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void run() {
		while(!over){
			send();
		}
	}
	public void finish(){
		setOver(true);
	}
	public void close(){
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void setOver(boolean over){
		this.over = over;
	}
	private void setSendOver(boolean sendOver){
		this.sendOver = sendOver;
	}
	public boolean isOver(){
		return over && sendOver;
	}
}
