package org.neo.nim.client.command;

import org.neo.nim.client.common.ClientInfo;
import org.neo.nim.client.service.EchoService;
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
public class EchoInfoCommand implements InnerCommand {

    private final static Logger LOGGER = LoggerFactory.getLogger(EchoInfoCommand.class);

    @Resource
    private ClientInfo clientInfo;

    @Resource
    private EchoService echoService;

    @Override
    public void process(String msg) {
        echoService.echo("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        echoService.echo("client info={}", clientInfo.get().getUserName());
        echoService.echo("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
