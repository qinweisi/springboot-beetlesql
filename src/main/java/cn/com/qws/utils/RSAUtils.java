package cn.com.qws.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

/**
 * @Author qinweisi
 * @Description RAS非对称加密
 **/
public class RSAUtils {

    /**
     * 生成RAS公钥与私钥字符串，直接返回
     *
     * @return
     */
    public static HashMap<String, String> getKeys() {
        HashMap<String, String> map = new HashMap<String, String>();
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到公钥字符串
        String publicKey = base64ToStr(keyPair.getPublic().getEncoded());
        // 得到私钥字符串
        String privateKey = base64ToStr(keyPair.getPrivate().getEncoded());
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        return map;
    }

    /**
     * 根据公钥字符串加载公钥
     *
     * @param publicKeyStr 公钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法", e);
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法", e);
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空", e);
        }
    }

    /**
     * 根据私钥字符串加载私钥
     *
     * @param privateKeyStr 私钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法", e);
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法", e);
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空", e);
        }
    }

    /**
     * 公钥加密
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return base64ToStr(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥解密
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("密文数据已损坏");
        }
    }

    public static String base64ToStr(byte[] b) {
        return javax.xml.bind.DatatypeConverter.printBase64Binary(b);
    }

    public static byte[] strToBase64(String str) {
        return javax.xml.bind.DatatypeConverter.parseBase64Binary(str);
    }

    /**
     * @Author qinweisi
     * @Description 测试
     **/
    public static void main(String[] args) throws Exception {
        //初始化阶段，初始化后生成秘钥对
        //公钥发送给消息发送方用于加密传输数据；私钥严格保存于消息接收方，收到加密的消息之后进行解密
        HashMap<String, String> map = RSAUtils.getKeys();
        String privateKeyStr = map.get("privateKey");
        String publicKeyStr = map.get("publicKey");
        publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdz4fn/Skq0CUkpeoqMYYiY+apnyKAhA5HCaQ1v6fIQraHUrH8AlzZjlgjwn9b1UD1BlXqeCf21AySWj845RHFE1votWzUGN7GN//2fcolxu2zcHM+clYHAFvYqXxZETNgTDr0AxB/P2QtEr0CvvRMoUpmlWhm/bEUHY5o7fBw+wIDAQAB";
        privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ3Ph+f9KSrQJSSl6ioxhiJj5qmfIoCEDkcJpDW/p8hCtodSsfwCXNmOWCPCf1vVQPUGVep4J/bUDJJaPzjlEcUTW+i1bNQY3sY3//Z9yiXG7bNwcz5yVgcAW9ipfFkRM2BMOvQDEH8/ZC0SvQK+9EyhSmaVaGb9sRQdjmjt8HD7AgMBAAECgYEAgcNBTIhFzpeCw0TObM12L1cYN0fMNgLgPpd1+GBU1X3N2y4rfj5vHNFINndbBCcxl4kTIo6UxgiNIdalf+IUHjpq6ibIvJEYsum/tyAQX5OulM1I34ykx5ydW6vFQ7YRPLaoqdW/YkBvD/R2h5f3IhzOhLwW4xrN3NWQp7HdSOECQQDvjA30dlYg+Ki1AqNNpdd10dHIGsqqwUIyPj/1xWeiknHB7oOBQ/88UWOI+nikCFLBjErxHY+Z1Cjc2br9w1drAkEAqKZPAnL4/2wkKQa3oQkEFwTbtLLIrtWM2hAy3pt0Uj1nrbnf4XQ3dpDx0K7DShYKmwYHLuZPbC5mvq/WYuMAsQJAZMeTh2nRyoVS1PSLJ7vB7fucOoerBDPSWbj+x7OJakgaO/DQuq2U0BedvJ9D6h7mUi9nyBEbvSgZGPExTlnEiQJBAIrysofXB/URON2gTixcDEWW2BkRb6wz/18uF2s2cujK9J+6U40hvgxusPx48Cca79P3bHWkhO0hcb9LifthAZECQGabimtuUtSJpuCACERs052+a1ptpTRclFt2adVqDFC0Fpq6N42C/Au6yKlWVjPPBUpO0vY5kDLLDWGb8GJ42JQ=";
        System.out.println("初始化私钥为：" + privateKeyStr);
        System.out.println("初始化公钥为：" + publicKeyStr);

        //消息发送方
        String originData = "123231245432123123";
        System.out.println("信息原文：" + originData);
        String encryptData = RSAUtils.encrypt(RSAUtils.loadPublicKey(publicKeyStr), originData.getBytes());
        System.out.println("加密后：" + encryptData);

        //消息接收方
        String decryptData = RSAUtils.decrypt(RSAUtils.loadPrivateKey(privateKeyStr), RSAUtils.strToBase64(encryptData));
        System.out.println("解密后：" + decryptData);
    }
}