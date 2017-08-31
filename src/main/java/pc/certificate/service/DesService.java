package pc.certificate.service;

import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by wu on 17-8-15.
 */
@Service
public class DesService {

    private static final byte[] key = "certific".getBytes();
    private static final String UTF8 = "utf8";

    /**
     * 加密函数
     * <p>
     * //     * @param data
     * 加密数据
     * //     * @param key
     * 密钥
     *
     * @return 返回加密后的数据
     */
    public String encrypt(String a) {

        byte[] data = new byte[0];
        try {
            data = a.getBytes(UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {

            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // using DES in ECB mode
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//算法/模式/补码

            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

            // 执行加密操作
            byte encryptedData[] = cipher.doFinal(data);

            return byteToHexString(encryptedData);

        } catch (Exception e) {
            System.err.println("DES算法，加密数据出错!");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密函数
     * <p>
     * //     * @param data
     * 解密数据
     * //     * @param key
     * 密钥
     *
     * @return 返回解密后的数据
     */
    public String decrypt(String data) {

        byte[] b = new byte[data.length() / 2];
        int i;
        for (i = 0; i < data.length() / 2; i++) {
            String s2 = data.substring(i * 2, i * 2 + 2);
            b[i] = (byte) (Integer.parseInt(s2, 16) & 0xff);
        }


        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // byte rawKeyData[] = /* 用某种方法获取原始密匙数据 */;

            // 从原始密匙数据创建一个DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // using DES in ECB mode
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

            // 正式执行解密操作
            byte decryptedData[] = cipher.doFinal(b);

            return new String(decryptedData, UTF8);
        } catch (Exception e) {
            System.err.println("DES算法，解密出错。");
            e.printStackTrace();
        }

        return null;
    }


    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    public String EncoderByMd5(String uppassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        // 加密后的字符串
        String newstr = base64en.encode(md5.digest(uppassword.getBytes(UTF8)));
        return newstr;
    }

}
