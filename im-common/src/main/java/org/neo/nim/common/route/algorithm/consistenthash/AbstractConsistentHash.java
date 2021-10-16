package org.neo.nim.common.route.algorithm.consistenthash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description : Consistent hash algorithm abstract class
 */
public abstract class AbstractConsistentHash {

    /**
     * Add one new node
     *
     * @param key   long
     * @param value String
     */
    protected abstract void add(long key, String value);

    /**
     * Sort nodes, data structure itself support sort,  which don't require to be rewritten
     */
    protected void sort() {
    }

    /**
     * Retrieves a node based on the current key according to the rules of the consistent hash algorithm
     *
     * @param value String
     * @return String
     */
    protected abstract String getFirstNodeValue(String value);

    /**
     * Get a service node by passing in the node list and client information
     *
     * @param values List<String>
     * @param key    String
     * @return Strign
     */
    public String process(List<String> values, String key) {

        for (String value : values) {
            add(hash(value), value);
        }
        sort();

        return getFirstNodeValue(key);
    }

    /**
     * hash calculation
     *
     * @param value String
     * @return Long
     */
    public Long hash(String value) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes = null;
        try {
            keyBytes = value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unknown string :" + value, e);
        }

        md5.update(keyBytes);
        byte[] digest = md5.digest();

        // hash code, Truncate to 32-bits
        long hashCode = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);

        long truncateHashCode = hashCode & 0xffffffffL;
        return truncateHashCode;
    }
}