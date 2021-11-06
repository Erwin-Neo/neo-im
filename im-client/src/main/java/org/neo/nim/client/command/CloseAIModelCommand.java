package org.neo.nim.client.command;

import org.neo.nim.client.service.EchoService;
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
public class CloseAIModelCommand implements  InnerCommand {

    private final static Logger LOGGER = LoggerFactory.getLogger(CloseAIModelCommand.class);

    @Resource
    private MsgHandle msgHandle ;

    @Resource
    private EchoService echoService ;

    @Override
    public void process(String msg) {
        msgHandle.closeAIModel();

        echoService.echo("\033[31;4m" + "｡ﾟ(ﾟ´ω`ﾟ)ﾟ｡  AI offline！" + "\033[0m");
    }
}
