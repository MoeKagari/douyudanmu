package douyudanmu;

import douyudanmu.tool.ZhiboStart;

/**
 * ������ʼ��zhiboStart
 * @author MoeKagari
 *
 */
public class MainStart {
	
	public static void main(String[] args) {
		
		String[] name = {
				//"457896",
				"414818"
		};//�������֣�����ҳ�����ĵ�ַ�����
		int zhiboxianlu = 0;//ֱ����·��0��ʼ�����ܹ��󣬹�����0
		int danmuxianlu = 0;//��Ļ��·��0��ʼ�����ܹ��󣬹�����0
		
		int len = name.length;
		ZhiboStart[] zhiboStart = new ZhiboStart[len];
		for(int i = 0;i < len;i++)
			zhiboStart[i] = new ZhiboStart(name[i], zhiboxianlu, danmuxianlu,len);
	}
	
}
