package me.chyc.utils;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * linux系统wget命令的java实现
 * Created by yicun.chen on 9/23/14.
 */
public class WgetUtil {
    /**
     * @param args
     */
    public static void main(String[] args) {

        //实现类似 wget http://www.sohu.com -q -O /root/test/sohu.txt 的功能
        String url = "http://www.sohu.com";
        String location = "/root/test/sohu.txt";
        Boolean ret = WGet(url, location, location + ".temp");
        System.out.println("ret==" + ret);
    }

    public static boolean WGet(String url, String location,
                               String location_temp) {
        boolean ret = true;
        String retData = getContent(url);
        System.out.println(url + ":return:" + retData);
        if (retData == null) {
            ret = false;
            return ret;
        }
        retData = retData.trim();
        if (retData.length() == 0) {
            System.out.println("系统发生错误!接口返回数据为空.");
            ret = false;
            return ret;
        }
        String errormsg = "";
        if ((retData.indexOf("error") > -1) || (retData.indexOf("Exception") > -1)) {
            ret = false;
            return ret;
        }
        try {
            FileOutputStream fos = new FileOutputStream(location_temp);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(retData);
            writer.close();
            fos.close();
            File temp = new File(location_temp);
            File fileDir = new File(temp.getParent());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            if (temp.exists()) {
                File dest = new File(location);
                File dest_bak = new File(location + "_bak_" + System.currentTimeMillis());
                if (dest.exists()) {
                    dest.renameTo(dest_bak);
                    dest.delete();
                }
                temp.renameTo(dest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return ret;
    }

    /**
     * 从某个接口取返回数据内容
     *
     * @param url
     * @return
     */
    public static String getContent(String url) {
        String content = null;
        int retry = 3; // 重试次数
        for (int i = 0; i < retry; i++) {
            content = tryGetContent(url);
            if (content != null) {
                break;
            }
        }
        return content;
    }

    /**
     * 从某个接口取返回数据内容
     *
     * @param url
     * @return
     */
    public static String tryGetContent(String url) {
        try {
            return fileGetContents(url);
        } catch (IOException e) {
            System.out.println("Error getURL:" + url);
        }
        return null;
    }


    /**
     * 从某个接口取返回数据内容
     *
     * @param url
     * @return
     */
    public static String fileGetContents(String strUrl) throws IOException {
        URL url1 = null;
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        InputStreamReader ins = null;
        try {
            url1 = new URL(strUrl);
            connection = (HttpURLConnection) url1.openConnection();
            connection.setConnectTimeout(2 * 1000);
            connection.setReadTimeout(2 * 1000);
            connection.connect();
            ins = new InputStreamReader(connection.getInputStream());
            reader = new BufferedReader(ins);
            StringBuffer sb = new StringBuffer();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (IOException e) {
            System.out.println("Error getURL:" + strUrl);
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}
