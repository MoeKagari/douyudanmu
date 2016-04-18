package douyudanmu.thread;

import douyudanmu.tool.HtmlPage;
import douyudanmu.tool.Parse;
import douyudanmu.tool.ZhiboStart;

public class ReTitleThread extends Thread implements MyThread {
	private ZhiboStart zhiboStart;
	
	private boolean over;
	private boolean reTitleOver;
	
	public ReTitleThread( ZhiboStart zhiboStart) {
		this.zhiboStart = zhiboStart;
	}
	
	private void retitle(){
		try {
			long total = 60000;
			long per = 5000;
			long count = total/per;
			while(count > 0){
				Thread.sleep(5000);//ÿ5����һ��over
				count--;
				if(over){//���over��������reTitleOver=true��������
					reTitleOver = true;
					return;
				}
			}
			Thread.sleep(total % per);
			//ÿ60����һ�α��������״̬
			
			String code = HtmlPage.getHtmlContent(zhiboStart.getRoomUrl());
			if(code == null) return;
			boolean online = Parse.parseOnline(code);
			String roomTitle = Parse.parseRoomTitle(code);
			zhiboStart.setOnline(online);
			zhiboStart.setRoomTitle(roomTitle);
			if(!zhiboStart.getRoomTitle().equals(roomTitle) || zhiboStart.getOnline() != online)
				zhiboStart.reTitle(roomTitle, online);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(!over){
			retitle();
		}
	}
	public void finish() {
		over = true;
	}
	public void close() {}
	
	
	
	
	public boolean isOver(){
		return over && reTitleOver;
	}
}
