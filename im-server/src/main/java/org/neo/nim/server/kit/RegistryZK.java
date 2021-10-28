package org.neo.nim.server.kit;

import org.neo.nim.server.config.AppConfiguration;
import org.neo.nim.server.util.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class RegistryZK implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(RegistryZK.class);

    private ZKit zKit;

    private AppConfiguration appConfiguration;

    private String ip;
    private int timServerPort;
    private int httpPort;

    public RegistryZK(String ip, int timServerPort, int httpPort) {
        this.ip = ip;
        this.timServerPort = timServerPort;
        this.httpPort = httpPort;
        zKit = SpringBeanFactory.getBean(ZKit.class);
        appConfiguration = SpringBeanFactory.getBean(AppConfiguration.class);
    }

    @Override
    public void run() {
        // create parent node
        zKit.createRootNode();

        if (appConfiguration.isZkSwitch()) {
            String path = appConfiguration.getZkRoot() + "/ip-" + ip + ":" + timServerPort + ":" + httpPort;
            zKit.createNode(path);
            logger.info("Registry zookeeper success, msg=[{}]", path);
        }
    }
}
