package org.neo.nim.client.command;

import com.vdurmont.emoji.EmojiParser;
import org.neo.nim.client.service.EchoService;
import org.neo.nim.client.service.MsgHandle;
import org.neo.nim.common.data.construct.RingBufferWheel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Service
public class DelayMsgCommand implements InnerCommand {

    @Resource
    private EchoService echoService;

    @Resource
    private MsgHandle msgHandle;

    @Resource
    private RingBufferWheel ringBufferWheel;

    @Override
    public void process(String msg) {
        if (msg.split(" ").length <= 2) {
            echoService.echo("incorrect command, :delay [msg] [delayTime]");
            return;
        }

        String message = msg.split(" ")[1];
        Integer delayTime = Integer.valueOf(msg.split(" ")[2]);

        RingBufferWheel.Task task = new DelayMsgJob(message);
        task.setKey(delayTime);
        ringBufferWheel.addTask(task);
        echoService.echo(EmojiParser.parseToUnicode(msg));
    }

    private class DelayMsgJob extends RingBufferWheel.Task {

        private String msg;

        public DelayMsgJob(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            msgHandle.sendMsg(msg);
        }
    }
}
