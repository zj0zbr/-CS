package until.redis;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by zy on 17-7-17.
 */


public class Redis {

    public static String get(String key) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = JedisPool.getJedis();
            result = jedis.get(key);
        } finally {
            JedisPool.returnResource(jedis);
        }
        return result;
    }


    public static void set(String key, String v)
    {
        Jedis jedis = null;
        try {
            jedis = JedisPool.getJedis();
            jedis.set(key,v);
        } finally {
            JedisPool.returnResource(jedis);
        }
    }


    public static void sadd(String key, String members){
        Jedis jedis = null;
        try {
            jedis = JedisPool.getJedis();
            jedis.sadd(key,members);
        } finally {
            JedisPool.returnResource(jedis);
        }
    }

    public static void zadd(String key, double score, String member){
        Jedis jedis = null;
        try {
            jedis = JedisPool.getJedis();
            jedis.zadd(key,score,member);
        } finally {
            JedisPool.returnResource(jedis);
        }
    }


    public static boolean zrem(String key, String members){
        Jedis jedis = null;
        try {
            jedis = JedisPool.getJedis();
            jedis.zrem(key,members);
        } finally {
            JedisPool.returnResource(jedis);
        }
        return true;
    }


    public static String zrange(String key, long start, long end){
        Jedis jedis = null;
        String str=null;
        Set<String> set = null;
        jedis = JedisPool.getJedis();
        set = jedis.zrange(key,start,end);
        JedisPool.returnResource(jedis);
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            str = it.next();
            System.out.println(str);
        }
        return str;
    }

    public Set<String> getKeys(){
        Jedis jedis = null;
        Set<String> set = null;
        jedis = JedisPool.getJedis();
        set = jedis.keys("*");
        return set;

    }

    public static Boolean exists(String key) {
        Jedis jedis=null;
        try {
            jedis=JedisPool.getJedis();
            jedis.exists(key);
        } finally {
            JedisPool.returnResource(jedis);
        }
        return jedis.exists(key);
    }

    public static Boolean sismember(String key, String member) {
        Jedis jedis=null;
        try{
            jedis=JedisPool.getJedis();
            jedis.sismember(key, member);
        }finally {
            JedisPool.returnResource(jedis);
        }
        return jedis.sismember(key,member);
    }

/*
    public static void main(String[] args) {
        JedisPool.createpool();
        Redis redis = new Redis();
//        redis.getKeys();
        for (String s:redis.getKeys()){
            System.out.println(s);
        }
    }
*/

}
