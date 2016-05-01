package douyudanmu.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 获取网页源代码
 * @author MoeKagari
 *
 */
public class HtmlPage {
	
	public static String getHtmlContent(String link) {
		try {
			StringBuffer code = new StringBuffer("");
			URL url = new URL(link);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
			String buff = null;
			while((buff = br.readLine()) != null){
				code.append(buff + "\n");
			}
			br.close();
			return code.toString();
		} catch (IOException e) {
			return null;
		}
	}
	
}
