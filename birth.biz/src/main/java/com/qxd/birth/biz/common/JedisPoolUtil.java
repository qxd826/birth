package com.qxd.birth.biz.common;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by xiangDong.qu on 16/2/16.
 */
@Slf4j
public class JedisPoolUtil {
    private static JedisPool slavePool;

    private static JedisPool masterPool;

    /**
     * Jedis 读IP地址
     */
    private static String slaveHost = null;

    /**
     * Jedis 读端口地址
     */
    private static String slavePort = null;

    /**
     * Jedis 读密码
     */
    private static String slavePassword = null;


    /**
     * Jedis 读最大并发数
     */
    private static String slaveIdle = null;

    /**
     * Jedis 读连接超时时间
     */
    private static String slaveTimeout = null;

    /**
     * Jedis 读最大连接数
     */
    private static String slaveMaxTotal = null;


    /**
     * Jedis 写IP地址
     */
    private static String masterHost = null;

    /**
     * Jedis 写端口地址
     */
    private static String masterPort = null;

    /**
     * Jedis 写密码
     */
    private static String masterPassword = null;


    /**
     * Jedis 写最大并发数
     */
    private static String masterIdle = null;

    /**
     * Jedis 写连接超时时间
     */
    private static String masterTimeout = null;

    /**
     * Jedis 写最大连接数
     */
    private static String masterMaxTotal = null;

    /**
     * Jeids db select
     */
    private static Integer dbIndex = 1;

    public static void init() {
        log.info("[Jedis] 初始化建立jedis连接池");
        createJedisMasterPool();
        createJedisSlavePool();
        log.info("[Jedis] 初始化建立jedis连接池结束. masterPool:{},slavePool:{}", masterPool, slavePool);
    }


    /**
     * 建立连接池 真实环境，一般把配置参数缺抽取出来。
     */
    private static void createJedisSlavePool() {

        // 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();

        // 设置最大连接数
        config.setMaxTotal(slaveMaxTotal == null ? 100 : Integer.parseInt(slaveMaxTotal));

        // 设置最大阻塞时间，记住是毫秒数milliseconds
        config.setMaxWaitMillis(slaveTimeout == null ? 3000 : Integer.parseInt(slaveTimeout));

        // 设置空间连接
        config.setMaxIdle(slaveIdle == null ? 50 : Integer.parseInt(slaveIdle));

        // 创建连接池
        slavePool = new JedisPool(config, slaveHost == null ? "115.29.245.103" : slaveHost, slavePort == null ? 6381 : Integer.parseInt(slavePort), slaveTimeout == null ? 3000 : Integer.parseInt(slaveTimeout), slavePassword == null ? "legend6381" : slavePassword);

    }

    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolSlaveInit() {
        if (slavePool == null)
            createJedisSlavePool();
    }

    /**
     * 获取一个jedis 对象
     *
     * @return
     */
    public static Jedis getSlaveJedis() {

        if (slavePool == null)
            poolSlaveInit();
        Jedis jedis = slavePool.getResource();
        jedis.select(dbIndex);
        return jedis;
    }

    /**
     * 归还一个连接
     *
     * @param jedis
     */
    public static void returnSlaveRes(Jedis jedis) {
        slavePool.returnResource(jedis);
    }

    /**
     * 建立连接池 真实环境，一般把配置参数缺抽取出来。
     */
    private static void createJedisMasterPool() {

        // 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();

        // 设置最大连接数
        config.setMaxTotal(masterMaxTotal == null ? 100 : Integer.parseInt(masterMaxTotal));

        // 设置最大阻塞时间，记住是毫秒数milliseconds
        config.setMaxWaitMillis(masterTimeout == null ? 3000 : Integer.parseInt(masterTimeout));

        // 设置空间连接
        config.setMaxIdle(masterIdle == null ? 50 : Integer.parseInt(masterIdle));

        // 创建连接池
        masterPool = new JedisPool(config, masterHost == null ? "115.29.245.103" : masterHost, masterPort == null ? 6380 : Integer.parseInt(masterPort), masterTimeout == null ? 3000 : Integer.parseInt(masterTimeout), masterPassword == null ? "legend6381" : masterPassword);

    }

    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolMasterInit() {
        if (masterPool == null)
            createJedisMasterPool();
    }

    /**
     * 获取一个jedis 对象
     *
     * @return
     */
    public static Jedis getMasterJedis() {

        if (masterPool == null)
            poolMasterInit();
        Jedis jedis = masterPool.getResource();
        jedis.select(dbIndex);
        return jedis;
    }

    /**
     * 归还一个连接
     *
     * @param jedis
     */
    public static void returnMasterRes(Jedis jedis) {
        masterPool.returnResource(jedis);
    }

    public void setSlaveHost(String slaveHost) {
        this.slaveHost = slaveHost;
    }

    public void setSlavePort(String slavePort) {
        this.slavePort = slavePort;
    }

    public void setSlavePassword(String slavePassword) {
        this.slavePassword = slavePassword;
    }

    public void setSlaveIdle(String slaveIdle) {
        this.slaveIdle = slaveIdle;
    }

    public void setSlaveTimeout(String slaveTimeout) {
        this.slaveTimeout = slaveTimeout;
    }

    public void setSlaveMaxTotal(String slaveMaxTotal) {
        this.slaveMaxTotal = slaveMaxTotal;
    }

    public void setMasterHost(String masterHost) {
        this.masterHost = masterHost;
    }

    public void setMasterPort(String masterPort) {
        this.masterPort = masterPort;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public void setMasterIdle(String masterIdle) {
        this.masterIdle = masterIdle;
    }

    public void setMasterTimeout(String masterTimeout) {
        this.masterTimeout = masterTimeout;
    }

    public void setMasterMaxTotal(String masterMaxTotal) {
        this.masterMaxTotal = masterMaxTotal;
    }

    public void setDbIndex(Integer dbIndex) {
        JedisPoolUtil.dbIndex = dbIndex;
    }
}
