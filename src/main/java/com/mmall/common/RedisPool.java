package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    private static JedisPool pool;
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));//连接池最大空闲状态
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle ","10"));
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle ","2"));//连接池最小空闲状态
    private static Boolean testOnBorrow = Boolean.getBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));//值为true,取得redis可用
    private static Boolean testOnReturn = Boolean.getBoolean(PropertiesUtil.getProperty("redis.test.return","true"));//值为true,放回redis可用


    private static String ip = PropertiesUtil.getProperty("redis.ip","localhost");//redis ip
    private static Integer port =  Integer.parseInt(PropertiesUtil.getProperty("redis.port","6379"));//redis port
    private static void init(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);

        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);

        jedisPoolConfig.setBlockWhenExhausted(true);//连接耗尽时，是否阻塞，默认为true

        pool = new JedisPool(jedisPoolConfig,ip,port,1000*2);
    }

    static {
        init();
    }

    public static Jedis getResource(){
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis){
       pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }


}
