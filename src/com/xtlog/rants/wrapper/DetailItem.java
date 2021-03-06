package com.xtlog.rants.wrapper;

import com.xtlog.rants.pojo.Comment;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/5/2.
 */
public class DetailItem {

    private Integer rantId;

    private Integer userId;

    private Integer rantHidden;

    private String rantContent;

    private Integer rantValue;

    private Date rantDate;

    private String rantAvatar;

    //new
    private String userName;

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    private String userAvatar;

    private Integer thumbValue;

    private List<CommentItem> commentList;



    public Integer getThumbValue() {
        return thumbValue;
    }

    public void setThumbValue(Integer thumbValue) {
        this.thumbValue = thumbValue;
    }

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
        this.rantContent = rantContent;
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

    public String getRantAvatar() {
        return rantAvatar;
    }

    public void setRantAvatar(String rantAvatar) {
        this.rantAvatar = rantAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<CommentItem> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentItem> commentList) {
        this.commentList = commentList;
    }
}
