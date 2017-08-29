package pc.certificate.service;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/1/17.
 */
/*
用于生成唯一UUID标识符
生成随机数拼接，并以“A05”为尾椎表明为用户表的ID
 */
@Service
public class UseIdGenerate {

    /*
    调用此接口以输出UUID
    输出的ID格式为“hvweFgWuELOSWQqdzqPoC0A05”
     */
    public static String createid(String ss) {
        String miniuuid = getMiniuuid();
        return miniuuid + ss;
    }

    private static String getMiniuuid() {

        String uid = java.util.UUID.randomUUID().toString().replaceAll("-", "");


        //System.out.println(uid);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int a = Integer.valueOf(uid.substring(3 * i, 3 * i + 1), 16).intValue();
            int b = Integer.valueOf(uid.substring(3 * i + 1, 3 * i + 2), 16).intValue();
            int c = Integer.valueOf(uid.substring(3 * i + 2, 3 * i + 3), 16).intValue();

            int m = ((a << 2) & 0x3c) + ((b >> 2) & 0x03);
            int n = ((b << 4) & 0x30) + (c & 0x0f);
            sb.append(getchar(m));
            sb.append(getchar(n));
        }
        int a = Integer.valueOf(uid.substring(30, 31), 16).intValue();
        int b = Integer.valueOf(uid.substring(31, 32), 16).intValue();

        int m = ((a << 2) & 0x3c) + ((b >> 2) & 0x03);

        sb.append(getchar(m));
        int n = b & 0x03;

        sb.append(getlastchar(n));
        return sb.toString();
    }

    private static char getlastchar(int n) {
        if (n == 0) {
            return '0';//35
        } else if (n == 1) {
            return '1';//(char)36;
        } else if (n == 2) {
            return '2';//(char)37;
        } else if (n == 3) {
            return '3';//(char)38;
        } else {
            throw new RuntimeException("hhhh!!!");
        }
    }

    private static char getchar(int x) {
        int charint = 0;
        if ((x >= 0) && (x <= 9)) {//'0'-'9'
            charint = 48 + x;
        } else if (x == 10) {
            charint = 95;//'_'
        } else if (x == 11) {
            charint = 45;//'-'
        } else if ((x >= 12) && (x <= 37)) {
            charint = ((x - 12) + 65);//'A'-'Z'
        } else if (x >= 38 && x <= 63) {
            charint = ((x - 38) + 97);//'a'-'z'
        } else {
            throw new RuntimeException("hhhh!!!");
        }
        return (char) charint;
    }
}