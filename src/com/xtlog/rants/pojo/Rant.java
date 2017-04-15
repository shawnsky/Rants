package com.xtlog.rants.pojo;

import java.util.Date;

public class Rant {
    private Integer rantId;

    private Integer userId;

    private Integer rantHidden;

    private String rantContent;

    private Integer rantValue;

    private Date rantDate;

    public Integer getRantId() {
        return rantId;
    }

    public void setRantId(Integer rantId) {
        this.rantId = rantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRantHidden() {
        return rantHidden;
    }

    public void setRantHidden(Integer rantHidden) {
        this.rantHidden = rantHidden;
    }

    public String getRantContent() {
        return rantContent;
    }

    public void setRantContent(String rantContent) {
        this.rantContent = rantContent == null ? null : rantContent.trim();
    }

    public Integer getRantValue() {
        return rantValue;
    }

    public void setRantValue(Integer rantValue) {
        this.rantValue = rantValue;
    }

    public Date getRantDate() {
        return rantDate;
    }

    public void setRantDate(Date rantDate) {
        this.rantDate = rantDate;
    }
}