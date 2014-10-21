package me.chyc.utils;

import java.util.HashMap;

/**
 * Created by yicun.chen on 10/7/14.
 */
public class StringUtils {
    public static String getDigital(String string) {
        return string.replaceAll("[^0-9]", "");
    }

    /** 0 ~ 99999 */
    public static String toDigital(String string) {
        HashMap<Character, Integer> digitalMap = new HashMap<Character, Integer>();
        char[] digitalChar = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '百', '千', '万', 'O', 'o', '0'};
        for (int i = 0; i < 11; i++)
            digitalMap.put(digitalChar[i], i);
        digitalMap.put('百', 100);
        digitalMap.put('千', 1000);
        digitalMap.put('万', 10000);
        digitalMap.put('O', 0);
        digitalMap.put('o', 0);
        digitalMap.put('0', 0);
        StringBuffer numStr = new StringBuffer();
        boolean flag = false;
        for (char ch : string.toCharArray()) {
            if (digitalMap.containsKey(ch)) {
                if (digitalMap.get(ch) < 10)
                    numStr.append(digitalMap.get(ch));
                else
                    numStr.append(ch);
                flag = true;
            } else if (Character.isDigit(ch)) {
                numStr.append(ch);
            } else if (flag)
                break;
        }

        int num = 0;
        string = numStr.toString();
        int startIndex = 0;
        int endIndex = string.indexOf('万');
        if (startIndex < endIndex) {
            num += Integer.valueOf(string.substring(startIndex, endIndex)) * 10000;
            startIndex = endIndex+1;
        }
        endIndex = string.indexOf('千');
        if (startIndex < endIndex) {
            num += Integer.valueOf(string.substring(startIndex, endIndex)) * 1000;
            startIndex = endIndex+1;
        }
        endIndex = string.indexOf('百');
        if (startIndex < endIndex) {
            num += Integer.valueOf(string.substring(startIndex, endIndex)) * 100;
        }
        endIndex = string.indexOf('十');
        if (startIndex < endIndex) {
            num += Integer.valueOf(string.substring(startIndex, endIndex)) * 10;
            startIndex = endIndex+1;

        }
        num += Integer.valueOf(string.substring(startIndex));

        return String.valueOf(num);
    }

    public static int toInt(String string){
        return Integer.valueOf(getDigital(string));
    }
    public static void main(String args[]) {
        String line = "第9oo99张";
        String digital = StringUtils.toDigital(line);
        System.out.println(digital);

    }
}
