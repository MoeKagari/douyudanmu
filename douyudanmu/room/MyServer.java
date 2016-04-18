package douyudanmu.room;

public class MyServer {
	private String adress;
	private int port;
	
	public MyServer(String adress,int port) {
		this.adress = adress;
		this.port = port;
	}
	
	public String getAdress() {
		return adress;
	}
	public int getPort() {
		return port;
	}
}
