package org.neo.nim.common.enums;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class SystemCommandEnumTest {


    @Test
    public void getAllStatusCode() throws Exception {
        Map<String, String> allStatusCode = SystemCommandEnum.getAllStatusCode();
        for (Map.Entry<String, String> stringStringEntry : allStatusCode.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            Assert.assertNotNull(key);
            Assert.assertNotNull(value);
        }
    }


}