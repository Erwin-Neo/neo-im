package org.neo.nim.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @version : 1.0
 * @description : SystemCommandEnum
 */
public enum SystemCommandEnum {

    ALL(":all       ", "Get all commands", "PrintAllCommand"),
    ONLINE_USER(":olu       ", "Get all online users", "PrintOnlineUsersCommand"),
    QUIT(":q!        ", "Exit the program", "ShutDownCommand"),
    QUERY(":q         ", "[:q keyword] Query chat records", "QueryHistoryCommand"),
    AI(":ai        ", "Enable AI mode", "OpenAIModelCommand"),
    QAI(":qai       ", "Disable AI mode", "CloseAIModelCommand"),
    PREFIX(":pu        ", "Fuzzy matching user", "PrefixSearchCommand"),
    EMOJI(":emoji     ", "Emoji expression list", "EmojiCommand"),
    INFO(":info      ", "Obtain client information", "EchoInfoCommand"),
    DELAY_MSG(":delay     ", "delay message, :delay [msg] [delayTime]", "DelayMsgCommand");

    /**
     * Enumeration code
     */
    private final String commandType;

    /**
     * Enumeration description
     */
    private final String desc;

    /**
     * Implementation class
     */
    private final String clazz;


    /**
     * Build a StatusEnum
     *
     * @param commandType Enumeration code
     * @param desc        Enumeration description
     */
    private SystemCommandEnum(String commandType, String desc, String clazz) {
        this.commandType = commandType;
        this.desc = desc;
        this.clazz = clazz;
    }

    /**
     * Gets the enumeration value code
     *
     * @return Enumeration code
     */
    public String getCommandType() {
        return commandType;
    }

    /**
     * Implementation class
     *
     * @return class。
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * Get enumeration description
     *
     * @return Enumeration description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Gets the enumeration value code
     *
     * @return Enumeration code
     */
    public String code() {
        return commandType;
    }

    /**
     * Get enumeration description
     *
     * @return Enumeration description
     */
    public String message() {
        return desc;
    }

    /**
     * Gets all enumeration values
     *
     * @return All the enumeration values
     */
    public static Map<String, String> getAllStatusCode() {
        Map<String, String> map = new HashMap<String, String>(16);
        for (SystemCommandEnum status : values()) {
            map.put(status.getCommandType(), status.getDesc());
        }
        return map;
    }

    public static Map<String, String> getAllClazz() {
        Map<String, String> map = new HashMap<String, String>(16);
        for (SystemCommandEnum status : values()) {
            map.put(status.getCommandType().trim(), "com.tuling.tim.client.service.impl.command." + status.getClazz());
        }
        return map;
    }
}
