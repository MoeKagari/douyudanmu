package douyudanmu.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class HtmlPage {
	
	public static String getHtmlContent(String room_url) {
		try {
			StringBuffer code = new StringBuffer("");
			URL url = new URL(room_url);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
			String buff = null;
			while((buff = br.readLine()) != null){
				code.append(buff + "\n");
			}
			br.close();
			return code.toString();
		} catch (IOException e) {
			System.out.println("获取房间源代码失败，或许是房间不存在，或许网络有问题：\n" + room_url);
			return null;
		}
	}
	
}
