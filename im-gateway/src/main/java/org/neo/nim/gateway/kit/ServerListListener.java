package org.neo.nim.gateway.kit;

import org.neo.nim.gateway.config.AppConfiguration;
import org.neo.nim.gateway.util.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class ServerListListener  implements  Runnable {

    private static Logger logger = LoggerFactory.getLogger(ServerListListener.class);

    private ZKit zkUtil;

    private AppConfiguration appConfiguration;


    public ServerListListener() {
        zkUtil = SpringBeanFactory.getBean(ZKit.class);
        appConfiguration = SpringBeanFactory.getBean(AppConfiguration.class);
    }

    @Override
    public void run() {
        zkUtil.subscribeEvent(appConfiguration.getZkRoot());
    }
}
