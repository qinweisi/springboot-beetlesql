package cn.com.qws.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author qinweisi
 * @Description AES对称加密
 **/
public class AESUtils {

    public static final String KEY = "mkosjdufiw!.Qsaq";
    
    /**
     * @Author qinweisi
     * @Description 加密
     **/
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
     * @Author qinweisi
     * @Description 解密
     **/
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * @Author qinweisi
     * @Description 测试
     **/
    public static void main(String[] args) throws Exception {
        // 需要加密的字串
        String cSrc = "大丫";

        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "mkosjdufiw!.Qsaq";
        System.out.println(cSrc);
        // 加密
        String enString = Encrypt("ceshi", cKey);
        String enString1 = Encrypt("123456", cKey);

        System.out.println("加密后的字串是：" + enString);
        System.out.println("加密后的字串是：" + enString1);

        // 解密
        String DeString = Decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);
        System.out.println("解密后的字串是：" + Decrypt("nrbDFsnQbTYyl+UUKIUAw+B/wSh+jFWa2hcVKAS7bT4wYZa2d2gVPZzCK3NL9mMYIOJUQzihTNQemN2LfjufgUn5wmLtwMDVJsq0Cz4dcC7Xpy91iqkIsJNLZeMDYoYXOjdD+IafHh/mb1GY9keLx4jq/u+dCVp2XYIjK+hwMcsHI19METyzsOusqf/lLLLMjQwwTQPx2H1FMLUwwOZZ6xzZFi/hXwls9FSqwxMX3U23DAl5mcALlpl6Z1y2YV/kVhUAVa2rSKUtzB9oEnMiQCK0hcGRymqKRmguOMZDFKvaPnk2H5aXb7rXJIi+5eh93b8uDRD7ooFI72X5uIFLtODYu4Td87NcPOEOWSkUcJmwcj3qwZZEyBEwj05Lqxyd44mWGSdaPaMFcSZuBI6PXGgDR6dDAhggOwz4JY+5Tj0SmU+cI+ZOnu84VdHbg8QnOV7Wc5UNT2jrrxLTRiy3HCNALHV+Dffok62CpJTCM0PH8bCSXyDZZdZFr44T4iYhSnl8dPBN7+Tjgw3I1xhasxW+3KREW7onkYDdANq4fjqD62oPXiLCfrnNxXpRYeQRXLfV22wxSv1B443ynGtCVf9CV0Pf95Ur8XnqSwWuT0rCWtiA5XVQWj/oNN4ug5gi3AezArqNXDgBDtQkr9B7mA==", cKey));
    }

}
