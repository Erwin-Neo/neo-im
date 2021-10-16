package org.neo.nim.common.route.algorithm.consistenthash;

import org.neo.nim.common.data.construct.SortArrayMap;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :    Custom SortArrayMap implementation
 */
public class SortArrayMapConsistentHash extends AbstractConsistentHash {

    private SortArrayMap sortArrayMap = new SortArrayMap();

    /**
     * Virtual node size
     */
    private static final int VIRTUAL_NODE_SIZE = 2;

    @Override
    public void add(long key, String value) {
        sortArrayMap.clear();
        for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
            Long hash = super.hash("vir" + key + i);
            sortArrayMap.add(hash, value);
        }
        sortArrayMap.add(key, value);
    }

    @Override
    public void sort() {
        sortArrayMap.sort();
    }

    @Override
    public String getFirstNodeValue(String value) {
        long hash = super.hash(value);
        System.out.println("value=" + value + " hash = " + hash);
        return sortArrayMap.firstNodeValue(hash);
    }
}
