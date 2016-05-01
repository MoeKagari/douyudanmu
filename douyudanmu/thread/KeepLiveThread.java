package douyudanmu.thread;

import java.io.IOException;
import java.io.OutputStream;

import douyudanmu.ZhiboStart;
import douyudanmu.tool.Conmunication;
import douyudanmu.tool.Parse;

public class KeepLiveThread extends Thread {
	private OutputStream os;
	private ZhiboStart zhiboStart;
	
	private boolean over = false;
	private boolean sendOver = false;
	
	private byte[] keepLiveMessage;
	
	public KeepLiveThread(OutputStream os,ZhiboStart zhiboStart) {
		this.os = os;
		this.zhiboStart = zhiboStart;
		keepLiveMessage = Parse.getByteArray(Conmunication.getKeepLiveMessage());
	}
	
	
	private void send(){
		try {
			long total = 40000;
			long per = 5000;
			long count = total/per;
			while(count > 0){
				Thread.sleep(per);//ÿ5����һ��over
				count--;
				if(over){//���over��������sendOver=true��������
					sendOver = true;
					return;
				}
			}
			Thread.sleep(total % per);
			
			//ÿ40�뷢��һ��keeplive��
			os.write(keepLiveMessage);
			//zhiboStart.printMessage("KeepLive......");
		} catch (InterruptedException | IOException e) {
			zhiboStart.printMessage("KeepLive����ʧ��......");
		}
	}
	
	
	
	public void run() {
		while(!over){
			send();
		}
	}
	
	
	
	public void finish(){
		over = true;
	}
	public boolean isOver(){
		return over && sendOver;
	}
}
