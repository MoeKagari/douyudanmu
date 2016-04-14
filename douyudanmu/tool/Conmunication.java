package douyudanmu.tool;

import java.util.Random;
import java.util.UUID;


public class Conmunication {
	static int len = 0;
	static int[] length = new int[]{len, 0x00, 0x00, 0x00};
	static int[] code = new int[]{len, 0x00, 0x00, 0x00};
	static int[] magic = new int[]{0xb1, 0x02, 0x00, 0x00};
	//message is here
	static int[] end = new int[]{0x00};
	
	
	
	private static void setLen(String message){
		len = 4 + 4 + (message == null ? 0 : message.length()) + 1;
		length[0] = len;
		code[0] = len;
	}
	private static String connect(String message){
		setLen(message);
		return Parse.intArrayToString(length) +
				Parse.intArrayToString(code) +
				Parse.intArrayToString(magic) +
				message +
				Parse.intArrayToString(end);
	}
	
	
	public static String getLoginMessageForZhiboRoute(int roomId){
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String vk = MD5.getMD5(timestamp + "7oE9nPEG9xXV69phU31FYCLUagKeYtsF" + uuid);
		String message = "type@=loginreq/username@=/ct@=0/password@=/roomid@=" +
						roomId + "/devid@=" +
						uuid + "/rt@=" +
						timestamp + "/vk@=" +
						vk + "/ver@=20150929/";
		
		return connect(message);
	}
	public static String getLoginMessageForDanmuRoute(int roomId){
		int visitor = new Random().nextInt();
		String message = "type@=loginreq/username@=visitor" +
						visitor + "/password@=1234567890123456/roomid@=" +
						roomId + "/";
		
		return connect(message);
	}
	public static String getJoinGroupMessage(int rid,int gid){
		String message = "type@=joingroup/rid@=" +
						rid + "/gid@=" +
						gid + "/";
		
		return connect(message);
	}
	public static String getKeepLiveMessage(){
		int tick = (int) (System.currentTimeMillis() / 1000);
		String message = "type@=keeplive/tick@=" + tick + "/";
		
		return connect(message);
	}
	
}
