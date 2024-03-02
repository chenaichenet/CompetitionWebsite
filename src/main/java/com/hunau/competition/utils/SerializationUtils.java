/**
 * FileName: SerializationUtil
 * Author:   嘉平十七
 * Date:     2021/3/13 16:24
 * Description: 序列化处理
 */
package com.hunau.competition.utils;

import java.io.*;

public class SerializationUtils {

    /**
     * 序列化对象
     * @param object
     * @return
     */
    public static byte[] serialize(Object object){
        if (object == null){
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != objectOutputStream){
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes){
        if (bytes == null){
            return null;
        }
        ObjectInputStream objectInputStream = null;
        Object object = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            object = objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != objectInputStream){
                    objectInputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return object;
    }
}