package com.blockchain.common.base.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author huangxl
 * @create 2019-02-19 14:53
 */
public class SerializableUtil {
    /**
     * java对象序列化成字节数组
     */
    public static byte[] serializeToBytes(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * java对象序列化成字符串
     */
    public static String serializeToString(Object object) {
        byte[] bytes = serializeToBytes(object);
        String str = Base64.encodeBase64String(bytes);
        return str;
    }

    /**
     * 字节数组反序列化成java对象
     */
    public static Object unserializeBytesToObject(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            Object object = ois.readObject();
            return object;
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 字符串反序列化成java对象
     */
    public static Object unserializeStringToObject(String str) {
        byte[] bytes = Base64.decodeBase64(str);
        return unserializeBytesToObject(bytes);
    }


}