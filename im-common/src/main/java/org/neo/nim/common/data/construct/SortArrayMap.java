package org.neo.nim.common.data.construct;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :    SortArrayMap
 */
public class SortArrayMap {

    private Node[] buckets;

    private static final int DEFAULT_SIZE = 10;

    /**
     * Array size
     */
    private int size = 0;

    public SortArrayMap() {
        buckets = new Node[DEFAULT_SIZE];
    }

    /**
     * Write in data
     *
     * @param key   Data key
     * @param value Data value
     */
    public void add(Long key, String value) {
        checkSize(size + 1);
        Node node = new Node(key, value);
        buckets[size++] = node;
    }

    /**
     * Verify whether capacity expansion is required
     *
     * @param size
     */
    private void checkSize(int size) {
        if (size >= buckets.length) {
            int oldLen = buckets.length;
            int newLen = oldLen + (oldLen >> 1);
            buckets = Arrays.copyOf(buckets, newLen);
        }
    }

    /**
     * Retrieve data clockwise
     *
     * @param key Key
     * @return Value
     */
    public String firstNodeValue(long key) {
        if (size == 0) {
            return null;
        }
        for (Node bucket : buckets) {
            if (bucket == null) {
                break;
            }
            if (bucket.key >= key) {
                return bucket.value;
            }
        }

        return buckets[0].value;

    }

    /**
     * Sort
     */
    public void sort() {
        Arrays.sort(buckets, 0, size, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.key > o2.key) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    public void print() {
        for (Node bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            System.out.println(bucket.toString());
        }
    }

    public int size() {
        return size;
    }

    public void clear() {
        buckets = new Node[DEFAULT_SIZE];
        size = 0;
    }

    /**
     * Data Node
     */
    private class Node {
        public Long key;
        public String value;

        public Node(Long key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
