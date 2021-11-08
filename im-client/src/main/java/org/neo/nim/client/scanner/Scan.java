package org.neo.nim.client.scanner;

import com.vdurmont.emoji.EmojiParser;
import org.neo.nim.client.config.AppConfiguration;
import org.neo.nim.client.service.EchoService;
import org.neo.nim.client.service.MsgHandle;
import org.neo.nim.client.service.MsgLogger;
import org.neo.nim.client.util.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class Scan implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Scan.class);

    /**
     * System params
     */
    private AppConfiguration configuration;

    private MsgHandle msgHandle;

    private MsgLogger msgLogger;

    private EchoService echoService;

    public Scan() {
        this.configuration = SpringBeanFactory.getBean(AppConfiguration.class);
        this.msgHandle = SpringBeanFactory.getBean(MsgHandle.class);
        this.msgLogger = SpringBeanFactory.getBean(MsgLogger.class);
        this.echoService = SpringBeanFactory.getBean(EchoService.class);
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String msg = sc.nextLine();

            // Check message
            if (msgHandle.checkMsg(msg)) {
                continue;
            }

            // Built-in system Commands
            if (msgHandle.innerCommand(msg)) {
                continue;
            }

            // Send a message
            msgHandle.sendMsg(msg);

            // Write chat history
            msgLogger.log(msg);

            echoService.echo(EmojiParser.parseToUnicode(msg));
        }
    }

}
