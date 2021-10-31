package org.neo.nim.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@SpringBootApplication
public class NIMClientApplication implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(NIMClientApplication.class);

    @Override
    public void run(String... args) throws Exception {

    }
}
