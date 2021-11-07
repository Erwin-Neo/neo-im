package org.neo.nim.client.command;

import org.neo.nim.client.NIMClient;
import org.neo.nim.client.service.EchoService;
import org.neo.nim.client.service.MsgLogger;
import org.neo.nim.client.service.RouteRequest;
import org.neo.nim.client.service.ShutDownMsg;
import org.neo.nim.common.data.construct.RingBufferWheel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Service
public class ShutDownCommand implements InnerCommand {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShutDownCommand.class);

    @Resource
    private RouteRequest routeRequest;

    @Resource
    private NIMClient imClient;

    @Resource
    private MsgLogger msgLogger;

    @Resource(name = "callBackThreadPool")
    private ThreadPoolExecutor callBackExecutor;

    @Resource
    private EchoService echoService;

    @Resource
    private ShutDownMsg shutDownMsg;

    @Resource
    private RingBufferWheel ringBufferWheel;

    @Override
    public void process(String msg) {
        echoService.echo("im client closing...");
        shutDownMsg.shutdown();
        routeRequest.offLine();
        msgLogger.stop();
        callBackExecutor.shutdown();
        ringBufferWheel.stop(false);
        try {
            while (!callBackExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                echoService.echo("thread pool closing");
            }
            imClient.close();
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException", e);
        }
        echoService.echo("im close success!");
        System.exit(0);
    }
}
