package com.xtlog.rants.pojo;

import java.util.Date;

/**
 * Created by admin on 2017/4/19.
 */
public class Star {
    private Integer starId;

    private Integer userId;

    private Integer rantId;

    private Integer starValue;

    private Integer starRead;

    private Date starDate;

    public Date getStarDate() {
        return starDate;
    }

    public void setStarDate(Date starDate) {
        this.starDate = starDate;
    }

    public Integer getStarRead() {
        return starRead;
    }

    public void setStarRead(Integer starRead) {
        this.starRead = starRead;
    }

    public Integer getStarId() {
        return starId;
    }

    public void setStarId(Integer starId) {
        this.starId = starId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRantId() {
        return rantId;
    }

    public void setRantId(Integer rantId) {
        this.rantId = rantId;
    }

    public Integer getStarValue() {
        return starValue;
    }

    public void setStarValue(Integer starValue) {
        this.starValue = starValue;
    }
}
