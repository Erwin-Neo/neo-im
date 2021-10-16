package org.neo.nim.common.data.construct;

import org.junit.Test;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class SortArrayMapTest {

    @Test
    public void testAdd() {
        SortArrayMap map = new SortArrayMap();
        for (int i = 0; i < 10; i++) {
            map.add(Long.valueOf(i), "127.0.0." + i);
        }
        map.print();
    }

    @Test
    public void testFirstNode() {
        SortArrayMap map = new SortArrayMap();

        map.add(100L, "127.0.0.100");
        map.add(10L, "127.0.0.10");
        map.add(8L, "127.0.0.8");
        map.add(1000L, "127.0.0.1000");

        map.sort();
        map.print();
        String value = map.firstNodeValue(101);
    }
}