package hazelcast;

import com.esotericsoftware.minlog.Log;
import org.domain.IdCount;
import org.domain.IdName;
import org.domain.IdValue;
import org.domain.NameValue;
import org.hazelcast.HazelStore;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TestHazelStore {

    protected void setUp() {
    }

    public void testWrite() {
        IdName john = IdName.Of("1", "john");
        boolean b = HazelStore.use(IdName.class).write("john", john);
        assertTrue(b);
        IdName john1 = IdName.Of("2", "john");
        boolean b1 = HazelStore.use(IdName.class).write("john", john1);
        assertTrue(b1);
        NameValue john2 = HazelStore.use(NameValue.class).read("john");
        IdName read = HazelStore.use(IdName.class).read("john");
        assertNotNull(read);
    }

    public void testWriteWithTtl() throws InterruptedException {
        IdName john = IdName.Of("1", "john");
        boolean b = HazelStore.use(IdName.class).write("john", john, 2, TimeUnit.SECONDS);
        assertTrue(b);
        IdName r1 = HazelStore.use(IdName.class).read("john");
        assertNotNull(r1);
        Thread.sleep(3000);
        IdName r2 = HazelStore.use(IdName.class).read("john");
        assertNull(r2);
    }

    public void testWriteWithTtlAndMaxIdle() throws InterruptedException {
        IdName john = IdName.Of("1", "john");
        boolean b = HazelStore.use(IdName.class).write("john", john, 1, TimeUnit.SECONDS);
        assertTrue(b);
        IdName r1 = HazelStore.use(IdName.class).read("john");
        assertNotNull(r1);
        Thread.sleep(1200);
        IdName r2 = HazelStore.use(IdName.class).read("john");
        assertNull(r2);
        IdName r3 = HazelStore.use(IdName.class).read("john");
        assertNull(r3);
    }


    public void testWriteList() {
        List<IdName> list = List.of(IdName.Of("1", "john"), IdName.Of("2", "jack"));
        boolean b = HazelStore.use(IdName.class).write("persons", list);
        assertTrue(b);
        List<IdName> persons = HazelStore.use(IdName.class).readList("persons");
        System.out.println(persons);
    }

    public void testWriteMap() {
        Map<String, IdName> john = Map.of("john", IdName.Of("1", "john"));
        HazelStore.use(Map.class).write("map", john);
        Map<String, IdName> map1 = HazelStore.use(IdName.class).readMap("map");
        System.out.println(map1);
    }


    public void testReplace() {
        IdName john = IdName.Of("1", "john");
        boolean b = HazelStore.use(IdName.class).write("john", john);
        assertTrue(b);
        IdName rp = IdName.Of("2", "john");
        boolean rpb = HazelStore.use(IdName.class).replace("john", rp);
        assertTrue(rpb);
        IdName read = HazelStore.use(IdName.class).read("john");
        assertEquals(rp, read);
    }

    public void testRemove() {
        IdName john = IdName.Of("1", "john");
        boolean b = HazelStore.use(IdName.class).write("john", john);
        assertTrue(b);
        IdName rd1 = HazelStore.use(IdName.class).read("john");
        assertEquals(john, rd1);
        boolean rb = HazelStore.remove("john");
        assertTrue(rb);
        IdName rd2 = HazelStore.use(IdName.class).read("john");
        assertNull(rd2);
    }

    public void testContainsKey() {
        IdName john = IdName.Of("1", "john");
        boolean b = HazelStore.use(IdName.class).write("john", john);
        assertTrue(b);
        boolean b1 = HazelStore.containsKey("john");
        assertTrue(b1);
        boolean b2 = HazelStore.containsKey("john2");
        assertFalse(b2);
    }

    public void testClear() {
        IdName john = IdName.Of("1", "john");
        boolean b = HazelStore.use(IdName.class).write("john", john);
        assertTrue(b);
        IdValue idValue = IdValue.Of("1", 10);
        boolean write = HazelStore.use(IdValue.class).write("1", idValue);
        assertTrue(write);

        IdName john1 = HazelStore.use(IdName.class).read("john");
        assertEquals(john, john1);
        IdValue idValue1 = HazelStore.use(IdValue.class).read("1");
        Log.info(idValue1.toString());
        HazelStore.clear();
        IdName john2 = HazelStore.use(IdName.class).read("john");
        assertNull(john2);
        IdValue idValue2 = HazelStore.use(IdValue.class).read("1");
        assertNull(idValue2);
    }

    public void testWriteComplex1() {
        List<Map<String, IdName>> listMaps = List.of(Map.of("1", IdName.Of("1", "john")));
        System.out.println(listMaps);
        HazelStore.use(Object.class).write("test", listMaps);
        List<Map<String, IdName>> test = HazelStore.use(Object.class).readComplex("test");
        System.out.println(test);
    }

    public void testWriteComplex2() {
        Map<String, List<IdName>> john = Map.of("1", List.of(IdName.Of("1", "john")));
        HazelStore.use(Object.class).write("test", john);
        Map<String, List<IdName>> test = HazelStore.use(Object.class).readComplex("test");
        System.out.println(test);
    }

    public void testWriteComplex3() {
        Map<String, List<IdName>> john = Map.of("1", List.of(IdName.Of("1", "john")));
        HazelStore.use(Object.class).write("test", john);
        Map<String, List<IdName>> test = HazelStore.use(Object.class).readComplex("test");
        System.out.println(test);
    }

    public void testQuery(){
        List<IdCount> ofed = List.of(IdCount.Of("1", 3), IdCount.Of("2", 4));
        HazelStore.use(Object.class).write("sql",ofed);
        List<Object> objects = HazelStore.use(Object.class).get("SELECT * FROM SQL WHERE id = ?", 3);
        System.out.println(objects);
    }

}
