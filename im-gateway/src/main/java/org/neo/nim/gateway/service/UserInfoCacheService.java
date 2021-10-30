package org.neo.nim.gateway.service;

import org.neo.nim.common.entity.NIMUserInfo;

import java.util.Set;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
public interface UserInfoCacheService {

    /**
     * Obtain user information by userID
     *
     * @param userId Unique user ID
     * @return
     * @throws Exception
     */
    NIMUserInfo loadUserInfoByUserId(Long userId);

    /**
     * Save and check the user login
     *
     * @param userId userId Unique user ID
     * @return true login false already
     * @throws Exception
     */
    boolean saveAndCheckUserLoginStatus(Long userId) throws Exception;

    /**
     * Clear the login status of a user
     *
     * @param userId
     * @throws Exception
     */
    void removeLoginStatus(Long userId) throws Exception;


    /**
     * query all online user
     *
     * @return online user
     */
    Set<NIMUserInfo> onlineUser();
}
