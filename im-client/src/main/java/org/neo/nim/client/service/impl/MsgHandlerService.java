package org.neo.nim.client.service.impl;

import org.neo.nim.client.NIMClient;
import org.neo.nim.client.common.ClientInfo;
import org.neo.nim.client.config.AppConfiguration;
import org.neo.nim.client.service.*;
import org.neo.nim.client.vo.req.GroupReqVO;
import org.neo.nim.client.vo.req.P2PReqVO;
import org.neo.nim.common.util.StringUtil;
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
public class MsgHandlerService implements MsgHandle {

    private final static Logger LOGGER = LoggerFactory.getLogger(MsgHandlerService.class);
    @Resource
    private RouteRequest routeRequest;

    @Resource
    private AppConfiguration configuration;

    @Resource(name = "callBackThreadPool")
    private ThreadPoolExecutor executor;

    @Resource
    private NIMClient timClient;

    @Resource
    private MsgLogger msgLogger;

    @Resource
    private ClientInfo clientInfo;

    @Resource
    private InnerCommandContext innerCommandContext;

    private boolean aiModel = false;

    @Override
    public void sendMsg(String msg) {
        if (aiModel) {
            aiChat(msg);
        } else {
            normalChat(msg);
        }
    }

    /**
     * Normal chat
     *
     * @param msg
     */
    private void normalChat(String msg) {
        String[] totalMsg = msg.split("::");
        if (totalMsg.length > 1) {
            //Private Chat
            P2PReqVO p2PReqVO = new P2PReqVO();
            p2PReqVO.setUserId(configuration.getUserId());
            p2PReqVO.setReceiveUserId(Long.parseLong(totalMsg[0]));
            p2PReqVO.setMsg(totalMsg[1]);
            try {
                p2pChat(p2PReqVO);
            } catch (Exception e) {
                LOGGER.error("Exception", e);
            }

        } else {
            //Group Chat
            GroupReqVO groupReqVO = new GroupReqVO(configuration.getUserId(), msg);
            try {
                groupChat(groupReqVO);
            } catch (Exception e) {
                LOGGER.error("Exception", e);
            }
        }
    }

    /**
     * AI model
     *
     * @param msg
     */
    private void aiChat(String msg) {
        msg = msg.replace("What", "");
        msg = msg.replace("Which", "");
        msg = msg.replace("?", "!");
        msg = msg.replace("？", "!");
        msg = msg.replace("You", "Me");
        System.out.println("AI:\033[31;4m" + msg + "\033[0m");
    }

    @Override
    public void groupChat(GroupReqVO groupReqVO) throws Exception {
        routeRequest.sendGroupMsg(groupReqVO);
    }

    @Override
    public void p2pChat(P2PReqVO p2PReqVO) throws Exception {
        routeRequest.sendP2PMsg(p2PReqVO);
    }

    @Override
    public boolean checkMsg(String msg) {
        if (StringUtil.isEmpty(msg)) {
            LOGGER.warn("Can't push empty message！");
            return true;
        }
        return false;
    }

    @Override
    public boolean innerCommand(String msg) {
        if (msg.startsWith(":")) {
            InnerCommand instance = innerCommandContext.getInstance(msg);
            instance.process(msg);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Shutdown system
     */
    @Override
    public void shutdown() {
        LOGGER.info("System is closing....");
        routeRequest.offLine();
        msgLogger.stop();
        executor.shutdown();
        try {
            while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                LOGGER.info("ThreadPool Shutdown....");
            }
            timClient.close();
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException", e);
        }
        System.exit(0);
    }

    @Override
    public void openAIModel() {
        aiModel = true;
    }

    @Override
    public void closeAIModel() {
        aiModel = false;
    }

}
