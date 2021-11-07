package org.neo.nim.client.command;

import org.neo.nim.client.service.EchoService;
import org.neo.nim.client.service.RouteRequest;
import org.neo.nim.client.vo.res.OnlineUsersResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Service
public class PrintOnlineUsersCommand implements InnerCommand {

    private final static Logger LOGGER = LoggerFactory.getLogger(PrintOnlineUsersCommand.class);


    @Resource
    private RouteRequest routeRequest;

    @Resource
    private EchoService echoService;

    @Override
    public void process(String msg) {
        try {
            List<OnlineUsersResVO.DataBodyBean> onlineUsers = routeRequest.onlineUsers();

            echoService.echo("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            for (OnlineUsersResVO.DataBodyBean onlineUser : onlineUsers) {
                echoService.echo("userId={}=====userName={}", onlineUser.getUserId(), onlineUser.getUserName());
            }
            echoService.echo("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }
    }
}
