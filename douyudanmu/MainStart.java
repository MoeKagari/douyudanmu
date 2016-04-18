package douyudanmu;

import douyudanmu.tool.ZhiboStart;

/**
 * 用来初始化zhiboStart
 * @author MoeKagari
 *
 */
public class MainStart {
	
	public static void main(String[] args) {
		String[] name = {
				"457896"
		};//房间名字，上网页看到的地址栏最后
		int zhiboxianlu = 1;//直播线路，0开始，不能过大，过大则0
		int danmuxianlu = 1;//弹幕线路，0开始，不能过大，过大则0
		
		int len = name.length;
		ZhiboStart[] zhiboStart = new ZhiboStart[len];
		for(int i = 0;i < len;i++)
			zhiboStart[i] = new ZhiboStart(name[i], zhiboxianlu, danmuxianlu,len);
	}
	
}
