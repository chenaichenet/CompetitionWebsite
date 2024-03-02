/**
 * FileName: RedisUtil
 * Author:   嘉平十七
 * Date:     2021/3/11 23:33
 * Description: Redis工具类
 */
package com.hunau.competition.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 指定缓存失效的时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time){
        try {
            if (time > 0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     * @param key
     * @return
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setList(final String key, String value){
        try {
            redisTemplate.opsForList().rightPush(key, value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public String get(final String key){
        try {
            return redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List getList(final String key){
        try {
            return redisTemplate.opsForList().range(key,0,-1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新缓存
     * @param key
     * @param value
     * @return
     */
    public boolean getAndSet(final String key, String value) {
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除缓存
     * @param key
     * @return
     */
    public boolean delete(final String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}