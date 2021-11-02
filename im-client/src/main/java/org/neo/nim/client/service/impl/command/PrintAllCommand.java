package org.neo.nim.client.service.impl.command;

import org.neo.nim.client.service.EchoService;
import org.neo.nim.client.service.InnerCommand;
import org.neo.nim.common.enums.SystemCommandEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Service
public class PrintAllCommand implements InnerCommand {

    private final static Logger LOGGER = LoggerFactory.getLogger(PrintAllCommand.class);

    @Resource
    private EchoService echoService;

    @Override
    public void process(String msg) {
        Map<String, String> allStatusCode = SystemCommandEnum.getAllStatusCode();
        echoService.echo("====================================");
        for (Map.Entry<String, String> stringStringEntry : allStatusCode.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            echoService.echo(key + "----->" + value);
        }
        echoService.echo("====================================");
    }
}
