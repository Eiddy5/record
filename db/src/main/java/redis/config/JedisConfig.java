package redis.config;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

public class JedisConfig {
    public static void main(String[] args) {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("192.168.9.140", 6379));
        jedisClusterNodes.add(new HostAndPort("192.168.9.141", 6379));
        jedisClusterNodes.add(new HostAndPort("192.168.9.142", 6379));
        jedisClusterNodes.add(new HostAndPort("192.168.9.143", 6379));
        jedisClusterNodes.add(new HostAndPort("192.168.9.144", 6379));
        jedisClusterNodes.add(new HostAndPort("192.168.9.145", 6379));

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(20);
        jedisPoolConfig.setMinIdle(5);
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, jedisPoolConfig);
        jedisCluster.set("test", "test");
        String test = jedisCluster.get("test");
        System.out.println(test);
        jedisCluster.close();
    }

}
