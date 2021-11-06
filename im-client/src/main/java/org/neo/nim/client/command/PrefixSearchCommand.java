package org.neo.nim.client.command;

import org.neo.nim.client.service.EchoService;
import org.neo.nim.client.service.RouteRequest;
import org.neo.nim.client.vo.res.OnlineUsersResVO;
import org.neo.nim.common.data.construct.TrieTree;
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
public class PrefixSearchCommand implements InnerCommand {

    private final static Logger LOGGER = LoggerFactory.getLogger(PrefixSearchCommand.class);

    @Resource
    private RouteRequest routeRequest;
    @Resource
    private EchoService echoService;

    @Override
    public void process(String msg) {
        try {
            List<OnlineUsersResVO.DataBodyBean> onlineUsers = routeRequest.onlineUsers();
            TrieTree trieTree = new TrieTree();
            for (OnlineUsersResVO.DataBodyBean onlineUser : onlineUsers) {
                trieTree.insert(onlineUser.getUserName());
            }

            String[] split = msg.split(" ");
            String key = split[1];
            List<String> list = trieTree.prefixSearch(key);

            for (String res : list) {
                res = res.replace(key, "\033[31;4m" + key + "\033[0m");
                echoService.echo(res);
            }

        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }
    }
}
