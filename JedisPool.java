package until.redis;


import DAO.config.ConfigInit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;


public class JedisPool {
//    static {
//        PropertyConfigurator.configure("./Logger/log4j.propertites");
//    }
//    private static Logger logger = Logger.getLogger(HttpInboundHandler.class);
//    private static ReentrantLock lock = new ReentrantLock();
//    private static ArrayBlockingQueue<Jedis> pool = new ArrayBlockingQueue<Jedis>(30);


    //Redis服务器IP
    private static String ADDR = "127.0.0.1";
    //Redis的端口号
    private static int PORT = 6379;
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    // pool2中修改如下：
    //maxActive  ==>  maxTotal
    //maxWait ==> maxWaitMillis
    private static int MAX_ACTIVE = 1024;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
    private static int TIMEOUT = 10000;
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    private static redis.clients.jedis.JedisPool jedisPool = null;
/**
     * 初始化Redis连接池
     */

    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new redis.clients.jedis.JedisPool(config, ADDR, PORT, TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void createpool(){
//        for (int i = 0; i < 50; i++){
//            try {
//                pool.put(new Jedis(ConfigInit.REDIS_IP, ConfigInit.REDIS_PORT));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

/*
*
     * 获取Jedis实例
     * @return
     */

    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static Jedis getJedis() throws InterruptedException {
//        return isalive(pool.take());
//    }


/**
     * 释放jedis资源
     * @param jedis
     */

    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

//    public static void returnJedis(Jedis jedis){
//        try {
//            pool.put(jedis);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }


     //验证是否存活
    private static Jedis isalive(Jedis jedis){
        if (!jedis.ping().equals("PONG")){
            return jedis = new Jedis(ConfigInit.REDIS_IP, ConfigInit.REDIS_PORT);
        }
        return jedis;
    }


}
