package org.neo.nim.gateway.kit;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.neo.nim.gateway.cache.ServerCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :    Zookeeper kit
 */
@Component
public class ZKit {

    private static Logger logger = LoggerFactory.getLogger(ZKit.class);


    @Resource
    private ZkClient zkClient;

    @Resource
    private ServerCache serverCache;


    /**
     * Listen for an event
     *
     * @param path String
     */
    public void subscribeEvent(String path) {
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChildren) throws Exception {
                logger.info("Clear and update local cache parentPath=[{}],currentChildren=[{}]", parentPath, currentChildren.toString());

                //update local cache, delete and save.
                serverCache.updateCache(currentChildren);
            }
        });


    }


    /**
     * get all server node from zookeeper
     *
     * @return List<String>
     */
    public List<String> getAllNode() {
        List<String> children = zkClient.getChildren("/route");
        logger.info("Query all node =[{}] success.", JSON.toJSONString(children));
        return children;
    }
}
