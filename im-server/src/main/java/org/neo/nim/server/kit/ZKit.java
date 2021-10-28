package org.neo.nim.server.kit;

import org.I0Itec.zkclient.ZkClient;
import org.neo.nim.server.config.AppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Component
public class ZKit {
    private static Logger logger = LoggerFactory.getLogger(ZKit.class);

    @Resource
    private ZkClient zkClient;

    @Resource
    private AppConfiguration appConfiguration;

    /**
     * Create the parent node
     */
    public void createRootNode() {
        boolean exists = zkClient.exists(appConfiguration.getZkRoot());
        if (exists) {
            return;
        }

        zkClient.createPersistent(appConfiguration.getZkRoot());
    }

    /**
     * Writes a specified node (temporary directory)
     *
     * @param path String
     */
    public void createNode(String path) {
        zkClient.createEphemeral(path);
    }
}
