package org.neo.nim.gateway.cache;

import com.google.common.cache.LoadingCache;
import org.neo.nim.gateway.kit.ZKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :    Service node cache
 */
@Component
public class ServerCache {

    private static Logger logger = LoggerFactory.getLogger(ServerCache.class);

    @Resource
    private LoadingCache<String, String> cache;

    @Resource
    private ZKit zkUtil;

    public void addCache(String key) {
        cache.put(key, key);
    }


    /**
     * Update all caches/delete first, then add
     *
     * @param currentChildren
     */
    public void updateCache(List<String> currentChildren) {
        cache.invalidateAll();
        for (String currentChild : currentChildren) {
            // currentChildren=ip-127.0.0.1:11212:9082 or 127.0.0.1:11212:9082
            String key;
            if (currentChild.split("-").length == 2) {
                key = currentChild.split("-")[1];
            } else {
                key = currentChild;
            }
            addCache(key);
        }
    }


    /**
     * Get a list of all services
     *
     * @return List<String>
     */
    public List<String> getServerList() {

        List<String> list = new ArrayList<>();

        if (cache.size() == 0) {
            List<String> allNode = zkUtil.getAllNode();
            for (String node : allNode) {
                String key = node.split("-")[1];
                addCache(key);
            }
        }
        for (Map.Entry<String, String> entry : cache.asMap().entrySet()) {
            list.add(entry.getKey());
        }
        return list;

    }

    /**
     * Rebuild cache list
     */
    public void rebuildCacheList() {
        updateCache(getServerList());
    }

}
