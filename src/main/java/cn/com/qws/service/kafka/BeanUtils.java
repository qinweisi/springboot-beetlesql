package cn.com.qws.service.kafka;

import java.io.*;

/**
 * @Description kafka使用转换对象类
 * @Author qinweisi
 * @Date 2019/8/22 14:12
 **/
public class BeanUtils {

    private BeanUtils() {
    }

    /**
     * 对象序列化为byte数组
     *
     * @param obj
     * @return
     */
    public static byte[] bean2Byte(Object obj) {
        byte[] bb = null;
        try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream(); ObjectOutputStream outputStream = new ObjectOutputStream(byteArray)) {
            outputStream.writeObject(obj);
            outputStream.flush();
            bb = byteArray.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bb;
    }

    /**
     * 字节数组转为Object对象
     *
     * @param bytes
     * @return
     */
    public static Object byte2Obj(byte[] bytes) {
        Object readObject = null;
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes); ObjectInputStream inputStream = new ObjectInputStream(in)) {
            readObject = inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readObject;
    }

}