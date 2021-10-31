package org.neo.nim.server;

import org.neo.nim.server.config.AppConfiguration;
import org.neo.nim.server.kit.RegistryZK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.net.InetAddress;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@SpringBootApplication
public class NIMServerApplication implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(NIMServerApplication.class);

    @Resource
    private AppConfiguration appConfiguration;

    @Value("${server.port}")
    private int httpPort;

    @Override
    public void run(String... args) throws Exception {

        String addr = InetAddress.getLocalHost().getHostAddress();
        Thread thread = new Thread(new RegistryZK(addr, appConfiguration.getTimServerPort(), httpPort));
        thread.setName("registry-zk");
        thread.start();
    }
}
