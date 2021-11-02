package org.neo.nim.client.service;

import org.springframework.stereotype.Component;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Component
public class ShutDownMsg {

    private boolean isCommand;

    /**
     * Set the user to exit voluntarily
     */
    public void shutdown() {
        isCommand = true;
    }

    public boolean checkStatus() {
        return isCommand;
    }
}
