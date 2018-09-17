package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * 项目名称：eab-ng-mmall
 * 包： com.mmall.util
 * 类名称：RedisPoolUtil.java
 * 类描述：操作redis的工具类
 * 创建人：wufuming
 * 创建时间：2018年09月15日
 */
@Slf4j
public class RedisPoolUtil {

    /**
     *
     * @param key 键
     * @param value 值
     * @param extTime 过期时间，单位是秒
     * @return
     */
    public static String setex(String key,String value,int extTime){

        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key,extTime,value);
        } catch (Exception e) {
            log.error("setex key:{} value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String set(String key,String value){

        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key,value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }
        RedisPool.returnResource(jedis);
        return result;
    }


    public static String get(String key){

        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{}  error",key,e);
            RedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static Long del(String key){

        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("delete key:{}  error",key,e);
            RedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    /**
     * set the key's period of validity
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key,int exTime){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key,exTime);
        } catch (Exception e) {
            log.error("expire key:{}  error",key,e);
            RedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static void main(String[] arges) {

        RedisPoolUtil.set("newkey", "newvalue");
        String value = RedisPoolUtil.get("newkey");

        RedisPoolUtil.setex("newkeyex", "newvalueex", 60 * 5);

        RedisPoolUtil.expire("newkey", 60 * 10);

        RedisPoolUtil.del("newkey");



    }


}