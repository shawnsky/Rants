package com.xtlog.rants.pojo;

import java.util.Date;

public class Comment {
    private Integer commentId;

    private Integer userId;

    private Integer commentHidden;

    private String commentContent;

    private Integer commentValue;

    private Date commentDate;

    private Integer rantId;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCommentHidden() {
        return commentHidden;
    }

    public void setCommentHidden(Integer commentHidden) {
        this.commentHidden = commentHidden;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public Integer getCommentValue() {
        return commentValue;
    }

    public void setCommentValue(Integer commentValue) {
        this.commentValue = commentValue;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Integer getRantId() {
        return rantId;
    }

    public void setRantId(Integer rantId) {
        this.rantId = rantId;
    }
}