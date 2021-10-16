package org.neo.nim.common.route.algorithm.random;

import org.neo.nim.common.enums.StatusEnum;
import org.neo.nim.common.exception.TIMException;
import org.neo.nim.common.route.algorithm.RouteHandle;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class RandomHandle implements RouteHandle {

    @Override
    public String routeServer(List<String> values, String key) {
        int size = values.size();
        if (size == 0) {
            throw new TIMException(StatusEnum.SERVER_NOT_AVAILABLE);
        }
        int offset = ThreadLocalRandom.current().nextInt(size);

        return values.get(offset);
    }
}
