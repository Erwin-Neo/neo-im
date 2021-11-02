package org.neo.nim.client.service;

import org.neo.nim.client.vo.req.GroupReqVO;
import org.neo.nim.client.vo.req.P2PReqVO;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :    Message handler
 */
public interface MsgHandle {


    /**
     * Unified send interface，contains: groupChat p2pChat
     *
     * @param msg
     */
    void sendMsg(String msg);

    /**
     * Group chat
     *
     * @param groupReqVO GroupChat Message  userId  is publish userID
     * @throws Exception
     */
    void groupChat(GroupReqVO groupReqVO) throws Exception;

    /**
     * Private chat
     *
     * @param p2PReqVO Private chat request
     * @throws Exception
     */
    void p2pChat(P2PReqVO p2PReqVO) throws Exception;


    // #TODO: The follow-up of the message processing can be optimized for the chain of responsibility pattern

    /**
     * Check the message
     *
     * @param msg
     * @return Can't be null, A few sensitive words can be added afterwards
     * @throws
     */
    boolean checkMsg(String msg);

    /**
     * Execute internal commands
     *
     * @param msg
     * @return Whether the current message should be skipped (if it contains ":")
     */
    boolean innerCommand(String msg);
    
    /**
     * Shut down the system
     */
    void shutdown();

    /**
     * Enable AI mode
     */
    void openAIModel();

    /**
     * Disable AI mode
     */
    void closeAIModel();
}
