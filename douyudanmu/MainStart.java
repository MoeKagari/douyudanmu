package douyudanmu;

import douyudanmu.tool.ZhiboStart;

/**
 * 用来初始化zhiboStart
 * @author MoeKagari
 *
 */
public class MainStart {
	private ZhiboStart[] zhiboStart = null;
	
	public MainStart(String[] name,int zhiboxianlu,int danmuxianlu) {
		int len = name.length;
		zhiboStart = new ZhiboStart[len];
		for(int i = 0;i < len;i++)
			zhiboStart[i] = new ZhiboStart(name[i], zhiboxianlu, danmuxianlu,len);
	}
}
