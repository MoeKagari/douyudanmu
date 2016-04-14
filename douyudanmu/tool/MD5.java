package douyudanmu.tool;

import java.security.MessageDigest;

public class MD5 {
    public static String getMD5(String string) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(string.getBytes());
            int len = b.length;
            char ch[] = new char[len * 2];
            for(int i = 0;i < len;i++){
            	ch[2 * i]     = hexDigits[b[i] >>> 4 & 0xf];
            	ch[2 * i + 1] = hexDigits[b[i] & 0xf];
            }
            return new String(ch).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}