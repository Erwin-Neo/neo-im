package org.neo.nim.client.command;

import org.neo.nim.client.service.MsgHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Service
public class OpenAIModelCommand implements InnerCommand {

    private final static Logger LOGGER = LoggerFactory.getLogger(OpenAIModelCommand.class);


    @Resource
    private MsgHandle msgHandle;

    @Override
    public void process(String msg) {
        msgHandle.openAIModel();
        System.out.println("\033[31;4m" + "Hello,I'm Neo AI Robot :D！" + "\033[0m");
    }
}
