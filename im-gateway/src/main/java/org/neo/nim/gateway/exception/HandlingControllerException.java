package org.neo.nim.gateway.exception;

import org.neo.nim.common.exception.TIMException;
import org.neo.nim.common.res.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@ControllerAdvice
public class HandlingControllerException {

    private static Logger logger = LoggerFactory.getLogger(HandlingControllerException.class);

    @ExceptionHandler(TIMException.class)
    @ResponseBody()
    public BaseResponse handleAllExceptions(TIMException ex) {
        logger.error("exception", ex);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ex.getErrorCode());
        baseResponse.setMessage(ex.getMessage());
        return baseResponse;
    }
}
