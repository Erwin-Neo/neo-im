package org.neo.nim.common.data.construct;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class TrieTreeTest {

    @Test
    public void testInsert() throws Exception {
        TrieTree trieTree = new TrieTree();
        trieTree.insert("abc");
        trieTree.insert("abcd");
    }

    @Test
    public void testAll() throws Exception {
        TrieTree trieTree = new TrieTree();
        trieTree.insert("ABC");
        trieTree.insert("abC");
        List<String> all = trieTree.all();
        String result = "";
        for (String s : all) {
            result += s + ",";
        }

        Assert.assertTrue("ABC,abC,".equals(result));
    }

    @Test
    public void testPrefixSea() throws Exception {
        TrieTree trieTree = new TrieTree();
        trieTree.insert("java");
        trieTree.insert("jsf");
        trieTree.insert("jsp");
        trieTree.insert("javascript");
        trieTree.insert("php");

        String result = "";
        List<String> ab = trieTree.prefixSearch("jav");
        for (String s : ab) {
            result += s + ",";
        }

        Assert.assertTrue(result.equals("java,javascript,"));
    }

    @Test
    public void testPrefixSearch() throws Exception {
        TrieTree trieTree = new TrieTree();
        trieTree.insert("Cde");
        trieTree.insert("CDa");
        trieTree.insert("ABe");

        List<String> ab = trieTree.prefixSearch("AC");
        Assert.assertTrue(ab.size() == 0);
    }
}