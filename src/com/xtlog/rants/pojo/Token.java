package com.xtlog.rants.pojo;

/**
 * Created by admin on 2017/4/30.
 */
public class Token {
    private String userToken;
    private Integer userId;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
