package org.neo.nim.common.entity;

/**
 * @version : 1.0
 * @description :    User Information
 */
public class NIMUserInfo {

    private Long userId;
    private String userName;

    public NIMUserInfo(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "NIMUserInfo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
