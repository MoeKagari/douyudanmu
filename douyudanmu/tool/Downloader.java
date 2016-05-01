package douyudanmu.tool;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * 下载网络资源到文件
 * @author MoeKagari
 *
 */
public class Downloader {

	public static void downloadToFile(String address, File file) {
		try {
			URL url = new URL(address);
			File parent = file.getParentFile();
			if(!parent.exists())
				parent.mkdirs();
			if (!file.exists())
				file.createNewFile();

			FileOutputStream fos = new FileOutputStream(file);
			DataInputStream dis = new DataInputStream(url.openStream());

			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = dis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}

			dis.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
