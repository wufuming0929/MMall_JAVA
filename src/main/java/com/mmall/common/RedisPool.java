package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 项目名称：eab-ng-mmall
 * 包： com.mmall.common
 * 类名称：RedisPool.java
 * 类描述：
 * 创建人：wufuming
 * 创建时间：2018年09月15日
 */
public class RedisPool {

    private static JedisPool jedisPool;
    private static Integer maxTotal = PropertiesUtil.getInt("redis.max.total",20);//最大连接数
    private static Integer maxIdle = PropertiesUtil.getInt("redis.max.idle",10);//最大空闲数
    private static Integer minIdle = PropertiesUtil.getInt("redis.min.idle",2);//最小空闲数
    private static Boolean testOnBorrow =PropertiesUtil.getBoolean("redis.test.borrow");//获取连接到时候，检查连接是否可用
    private static Boolean testOnReturn = PropertiesUtil.getBoolean("redis.test.return");//返回连接时，检查连接是否可用
    private static  String redisIp = PropertiesUtil.getProperty("redis.ip");//ip
    private static  Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));//端口

    private static void initPool(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        jedisPoolConfig.setBlockWhenExhausted(true);//连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。

        jedisPool = new JedisPool(jedisPoolConfig,redisIp,redisPort,2000);

    }

    //JVM启动时候，就加载进内存，实例化一次。
    static {
        initPool();
    }

    public static Jedis getJedis(){

        return jedisPool.getResource();
    }

    public static void returnBrokenResource(Jedis jedis){
        jedisPool.returnBrokenResource(jedis);
    }

    public static void returnResource(Jedis jedis){
        jedisPool.returnResource(jedis);
    }


    public static void main(String[] arges) {
        Jedis jedis = getJedis();
        jedis.setex("myname",10,"jessi");
        jedis.close();
        jedisPool.destroy();
        System.out.println("program is end!");
    }

}