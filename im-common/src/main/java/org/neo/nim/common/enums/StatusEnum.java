package org.neo.nim.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @version : 1.0
 * @description :   NIM  StatusEnum
 */
public enum StatusEnum {

    /**
     * SUCCESS
     */
    SUCCESS("9000", "SUCCESS"),
    /**
     * FALL_BACK
     */
    FALLBACK("8000", "FALL_BACK"),
    /**
     * INVALID ARGUMENT
     */
    VALIDATION_FAIL("3000", "Invalid argument"),
    /**
     * FAIL
     */
    FAIL("4000", "FAILURE"),

    /**
     * REPEAT LOGIN
     */
    REPEAT_LOGIN("5000", "Repeat login, log out an account please!"),

    /**
     * REQUEST FLOW LIMITING
     */
    REQUEST_LIMIT("6000", "Request flow limiting!"),

    /**
     * THE ACCOUNT IS OFFLINE
     */
    OFF_LINE("7000", "The account you selected is not online, please select again! "),

    /**
     * SERVER NOT AVAILABLE
     */
    SERVER_NOT_AVAILABLE("7100", "NIM server is not available, please try again later!"),

    /**
     * RECONNECT FAIL
     */
    RECONNECT_FAIL("7200", "Reconnect fail, continue to retry!"),

    /**
     * LOGIN INFORMATION DOES NOT MATCH
     */
    ACCOUNT_NOT_MATCH("9100", "The User information you have used is incorrect!"),

    ;


    /**
     * Enumeration code
     */
    private final String code;

    /**
     * Enumeration description
     */
    private final String message;

    /**
     * Build a StatusEnum
     *
     * @param code    Enumeration code
     * @param message Enumeration description
     */
    private StatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the enumeration value code
     *
     * @return Enumeration code
     */
    public String getCode() {
        return code;
    }

    /**
     * Get enumeration description
     *
     * @return Enumeration description
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the enumeration value code
     *
     * @return Enumeration code
     */
    public String code() {
        return code;
    }

    /**
     * Get enumeration description
     *
     * @return Enumeration description
     */
    public String message() {
        return message;
    }

    /**
     * Enumeration values via the enumeration values code
     *
     * @param code The enumeration value code search enumeration values
     * @return Enumeration values code corresponding to the enumeration values
     * @throws IllegalArgumentException If there is no corresponding StatusEnum code
     */
    public static StatusEnum findStatus(String code) {
        for (StatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("ResultInfo StatusEnum not legal:" + code);
    }

    /**
     * Get all the enumeration values
     *
     * @return All of the enumeration values
     */
    public static List<StatusEnum> getAllStatus() {
        List<StatusEnum> list = new ArrayList<StatusEnum>();
        for (StatusEnum status : values()) {
            list.add(status);
        }
        return list;
    }

    /**
     * Gets all enumeration values
     *
     * @return All the enumeration values
     */
    public static List<String> getAllStatusCode() {
        List<String> list = new ArrayList<String>();
        for (StatusEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }
}
