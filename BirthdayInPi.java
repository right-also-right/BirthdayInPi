package basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author roja
 * @description 生日在Π中的位置
 * @date 2021-03-23 15:31
 */
public class BirthdayInPi {

    public static void main(String[] args) throws Exception {
        System.out.println("输入pi文件目录(含文件名):");
        String filePath = new Scanner(System.in).useDelimiter("\n").next().trim();
        while (true) {
            doFind(filePath);
        }
    }

    private static void doFind(String filePath) throws Exception {
        System.out.println("/n输入想查询的字符串:");
        String inputStr = new Scanner(System.in).next().trim();
        byte[] inputBytes = inputStr.getBytes();

        File piFile = new File(filePath);
        long piSite = 0;

        int byteRead = 0;
        byte[] tempBytes = new byte[100];
        InputStream in = new FileInputStream(piFile);

        while ((byteRead = in.read(tempBytes)) != -1) {
            for (int i = 0; i < byteRead; i++) {
                piSite++;

                int passTime = 0;
                for (int j = i; j < byteRead && passTime < inputBytes.length; j++, passTime++) {
                    if (tempBytes[j] != inputBytes[passTime]) {
                        break;
                    }
                }

                if (inputBytes.length <= passTime) {
                    System.out.println("在pi小数点后" + (piSite - 2) + "位");
                    // 找到之后不在循环
                    return;
                }
            }

            if (byteRead == inputBytes.length - 1) {
                break;
            }

            // 减去回溯重查部分多余的计数
            piSite -= inputBytes.length - 1;

            // 回溯 inputBytes.length - 1 位 避免 bytes 100 位 与下一此读取首尾相接部分字符匹配产生的问题
            in.skip(-inputBytes.length + 1);
        }
    }
}
