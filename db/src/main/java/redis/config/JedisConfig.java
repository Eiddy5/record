package redis.config;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

public class JedisConfig {
    public static void main(String[] args) {
//        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
//
//        jedisClusterNodes.add(new HostAndPort("193.168.1.211", 6379));
//        jedisClusterNodes.add(new HostAndPort("193.168.1.212", 6379));
//        jedisClusterNodes.add(new HostAndPort("193.168.1.213", 6379));
//        jedisClusterNodes.add(new HostAndPort("193.168.1.214", 6379));
//        jedisClusterNodes.add(new HostAndPort("193.168.1.215", 6379));
//        jedisClusterNodes.add(new HostAndPort("193.168.1.216", 6379));
//
//
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(100);
//        jedisPoolConfig.setMaxIdle(20);
//        jedisPoolConfig.setMinIdle(5);
//        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, jedisPoolConfig);
//        jedisCluster.set("test", "test");
//        String test = jedisCluster.get("test");
//        System.out.println(test);
//        jedisCluster.del("test");
//        jedisCluster.close();

//        Jedis jedis = new Jedis("193.168.1.251", 6379);
//        jedis.set("test","test");
//        String s = jedis.get("test");
//        System.out.println(s);
//        jedis.del("test");
    }

}
