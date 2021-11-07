package org.neo.nim.client.command;

import org.neo.nim.client.service.EchoService;
import org.neo.nim.client.service.MsgLogger;
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
public class QueryHistoryCommand implements InnerCommand {

    private final static Logger LOGGER = LoggerFactory.getLogger(QueryHistoryCommand.class);

    @Resource
    private MsgLogger msgLogger;

    @Resource
    private EchoService echoService;

    @Override
    public void process(String msg) {
        String[] split = msg.split(" ");
        if (split.length < 2) {
            return;
        }
        String res = msgLogger.query(split[1]);
        echoService.echo(res);
    }
}
