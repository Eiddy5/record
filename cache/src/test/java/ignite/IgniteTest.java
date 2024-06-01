package ignite;

import org.apache.ignite.IgniteCache;
import org.ignite.client.IgniteClient;
import org.junit.jupiter.api.Test;

public class IgniteTest {

    @Test
    public void testIgnite(){
        IgniteCache<Object, Integer> cache = IgniteClient.getCache("test");
        cache.put("1",123);
        cache.put("2",456);
        Integer o = cache.get("1");
        Integer i = cache.get("2");
        System.out.println(o);
        System.out.println(i);
    }
}
