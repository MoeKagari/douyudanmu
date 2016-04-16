package douyudanmu.tool;

public class Log {
	
	public static void print(String message){
		System.out.println(message);
	}
	public static void print(String prefix,String message){
		print(prefix + message);
	}
	
	public static void messageFromZhiboServer(String message){
		String prefix = "从zhiboServer发来的消息：\n";
		print(prefix,message);
	}
	public static void messageToZhiboServer(String message){
		String prefix = "发给zhiboServer的消息：\n";
		print(prefix,message);
	}
	public static void messageFromDanmuServer(String message){
		String prefix = "从danmuServer发来的消息：\n";
		print(prefix,message);
	}
	public static void messageToDanmuServer(String message){
		String prefix = "发给danmuServer的消息：\n";
		print(prefix,message);
	}
	
	
	public static void receiveDanmu(String message){
		String prefix = "收到弹幕：\n";
		print(prefix,message);
	}
	
	public static void keepLive(){
		String prefix = "keepLive................";
		print(prefix,"");
	}
	
	public static void printDanmu(Message message){
		print(message.toString());
	}
}
