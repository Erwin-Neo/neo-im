package org.neo.nim.gateway.service.impl;

import org.neo.nim.common.entity.NIMUserInfo;
import org.neo.nim.gateway.constant.Constant;
import org.neo.nim.gateway.service.UserInfoCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public class UserInfoCacheServiceImpl implements UserInfoCacheService {

    /**
     * #TODO Local cache, in order to prevent memory explode, late may be substituted for LRU.
     */
    private final static Map<Long, NIMUserInfo> USER_INFO_MAP = new ConcurrentHashMap<>(64);

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public NIMUserInfo loadUserInfoByUserId(Long userId) {

        //Preferentially obtained from the local cache
        NIMUserInfo imUserInfo = USER_INFO_MAP.get(userId);
        if (imUserInfo != null) {
            return imUserInfo;
        }

        //load redis
        String sendUserName = redisTemplate.opsForValue().get(Constant.ACCOUNT_PREFIX + userId);
        if (sendUserName != null) {
            imUserInfo = new NIMUserInfo(userId, sendUserName);
            USER_INFO_MAP.put(userId, imUserInfo);
        }

        return imUserInfo;
    }

    @Override
    public boolean saveAndCheckUserLoginStatus(Long userId) throws Exception {

        Long add = redisTemplate.opsForSet().add(Constant.LOGIN_STATUS_PREFIX, userId.toString());
        return add != 0;
    }

    @Override
    public void removeLoginStatus(Long userId) throws Exception {
        redisTemplate.opsForSet().remove(Constant.LOGIN_STATUS_PREFIX, userId.toString());
    }

    @Override
    public Set<NIMUserInfo> onlineUser() {
        Set<NIMUserInfo> set = null;
        Set<String> members = redisTemplate.opsForSet().members(Constant.LOGIN_STATUS_PREFIX);
        assert members != null;
        for (String member : members) {
            if (set == null) {
                set = new HashSet<>(64);
            }
            NIMUserInfo imUserInfo = loadUserInfoByUserId(Long.valueOf(member));
            set.add(imUserInfo);
        }

        return set;
    }

}
