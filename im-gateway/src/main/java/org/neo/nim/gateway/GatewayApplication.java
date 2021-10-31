package org.neo.nim.gateway;

import org.neo.nim.gateway.kit.ServerListListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@SpringBootApplication
public class GatewayApplication implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(GatewayApplication.class);

    @Override
    public void run(String... args) throws Exception {

        Thread thread = new Thread(new ServerListListener());
        thread.setName("zk-listener");
        thread.start();
    }
}
