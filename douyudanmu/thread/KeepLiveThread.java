package douyudanmu.thread;

import java.io.IOException;
import java.io.OutputStream;

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
			long count = total/per;
			while(count > 0){
				Thread.sleep(5000);//每5秒检查一次over
				count--;
				if(over){//如果over结束，就sendOver=true结束返回
					sendOver = true;
					return;
				}
			}
			Thread.sleep(total % per);
			//每40秒发送一次keeplive包
			String message = Conmunication.getKeepLiveMessage();
			byte[] b = Parse.getByteArray(message);
			os.write(b);
			zhiboStart.printMessage("KeepLive......");
		} catch (InterruptedException | IOException e) {
			zhiboStart.printMessage("KeepLive发送失败......");
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
	public void close(){
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public boolean isOver(){
		return over && sendOver;
	}
}
