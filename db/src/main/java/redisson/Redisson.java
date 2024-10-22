package redisson;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

public class Redisson {
    private static RedissonClient instance;
    private static boolean a = false;


    static {
        init();
    }

    private static void init(){
        if (!a){
            a = true;
            Config config = new Config();
            config.setTransportMode(TransportMode.EPOLL);
            config.setCodec(new JsonJacksonCodec());
            config.useSingleServer().setAddress("");
            instance = org.redisson.Redisson.create();
        }
    }

    public static RedissonClient getClient(){
        return instance;
    }

    public static RTopic getTopic(String topicName){
        return instance.getTopic(topicName);
    }
}
