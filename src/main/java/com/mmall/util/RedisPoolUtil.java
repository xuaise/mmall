package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class RedisPoolUtil {

    public static Long expire(String key,int exTime){
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getResource();
            result = jedis.expire(key,exTime);
        } catch (Exception e) {
            log.error("set key:{} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    //存的单位是秒
    public static String setEx(String key,String value,int ExTime){
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getResource();
            result = jedis.setex(key,ExTime,value);
        } catch (Exception e) {
            log.error("set key:{},value:{} error",key,value,e);
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
            jedis = RedisPool.getResource();
            result = jedis.set(key,value);
        } catch (Exception e) {
            log.error("set key:{},value:{} error",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String get(String key){
        Jedis jedis = null;
        String value = null;

        try {
            jedis = RedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            log.error("get value:{} error",value,e);
            RedisPool.returnBrokenResource(jedis);
            return value;
        }
        RedisPool.returnResource(jedis);
        return value;
    }

    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getResource();
           result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{} error",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getResource();
        RedisPoolUtil.set("keyTest","test");
        RedisPoolUtil.setEx("keyex", "ex", 60*10);
        RedisPoolUtil.expire("keyTest", 60*20);
        RedisPoolUtil.del("keyTest");
        System.out.println("end");
    }

}
