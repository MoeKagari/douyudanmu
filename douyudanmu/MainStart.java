package douyudanmu;

/**
 * 用来初始化zhiboStart
 * @author MoeKagari
 *
 */
public class MainStart {
	
	public static void main(String[] args) {
		String tag = "457896";
		//房间名字，网页的地址栏最后
		int zhiboxianlu = 1;//直播线路，0开始，不能过大，过大则0
		int danmuxianlu = 1;//弹幕线路，0开始，不能过大，过大则0
		
		new ZhiboStart(tag,zhiboxianlu,danmuxianlu);
		
	}
	
}
