package douyudanmu.room;

/**
 * �����洢ZhiboServer
 * @author MoeKagari
 *
 */
public class ZhiboServer {
	private String adress;
	private int port;
	
	public ZhiboServer(String adress,int port) {
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
