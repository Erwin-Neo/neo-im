package org.neo.nim.client.command;

import org.neo.nim.client.service.impl.command.PrintAllCommand;
import org.neo.nim.client.util.SpringBeanFactory;
import org.neo.nim.common.enums.SystemCommandEnum;
import org.neo.nim.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Component
public class InnerCommandContext {

    private final static Logger LOGGER = LoggerFactory.getLogger(InnerCommandContext.class);

    /**
     * Get executor instances
     *
     * @param command Actuator instance
     * @return
     */
    public InnerCommand getInstance(String command) {

        Map<String, String> allClazz = SystemCommandEnum.getAllClazz();

        // Compatible with data that requires commands followed by parameters
        // :q neo
        String[] trim = command.trim().split(" ");
        String clazz = allClazz.get(trim[0]);
        InnerCommand innerCommand = null;
        try {
            if (StringUtil.isEmpty(clazz)) {
                clazz = PrintAllCommand.class.getName();
            }
            innerCommand = (InnerCommand) SpringBeanFactory.getBean(Class.forName(clazz));
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }

        return innerCommand;
    }

}
