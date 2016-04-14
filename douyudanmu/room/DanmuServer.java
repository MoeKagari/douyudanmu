package douyudanmu.room;

/**
 * ”√¿¥¥Ê¥¢DanmuServer
 * @author MoeKagari
 *
 */
public class DanmuServer {
	ZhiboServer server;
	
	public DanmuServer(String adress,int port) {
		this.server = new ZhiboServer(adress, port);
	}
	
	
	public String getAdress() {
		return server.getAdress();
	}
	public int getPort() {
		return server.getPort();
	}
}
