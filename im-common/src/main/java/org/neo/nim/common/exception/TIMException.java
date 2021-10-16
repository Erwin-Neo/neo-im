package org.neo.nim.common.exception;

import org.neo.nim.common.enums.StatusEnum;

/**
 * @version : 1.0
 * @description :
 */
public class TIMException extends GenericException {

    public TIMException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public TIMException(Exception e, String errorCode, String errorMessage) {
        super(e, errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public TIMException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public TIMException(StatusEnum statusEnum) {
        super(statusEnum.getMessage());
        this.errorMessage = statusEnum.message();
        this.errorCode = statusEnum.getCode();
    }

    public TIMException(StatusEnum statusEnum, String message) {
        super(message);
        this.errorMessage = message;
        this.errorCode = statusEnum.getCode();
    }

    public TIMException(Exception oriEx) {
        super(oriEx);
    }

    public TIMException(Throwable oriEx) {
        super(oriEx);
    }

    public TIMException(String message, Exception oriEx) {
        super(message, oriEx);
        this.errorMessage = message;
    }

    public TIMException(String message, Throwable oriEx) {
        super(message, oriEx);
        this.errorMessage = message;
    }

    public static boolean isResetByPeer(String msg) {
        if ("Connection reset by peer".equals(msg)) {
            return true;
        }
        return false;
    }
}
