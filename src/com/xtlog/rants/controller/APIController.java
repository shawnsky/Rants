package com.xtlog.rants.controller;


import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.xtlog.rants.pojo.*;
import com.xtlog.rants.service.*;
import com.xtlog.rants.wrapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by admin on 2017/4/20.
 */
@Controller
@RequestMapping(value = "/api",produces = "text/html;charset=UTF-8")
public class APIController {
    @Autowired
    private RantService rantService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private StarService starService;
    @Autowired
    private TokenService tokenService;




    @ResponseBody
    @RequestMapping(value = "/allRants", method = {RequestMethod.GET})
    public List<RantItem> allRants(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        HashMap<Integer, Integer> rantId2Value = new HashMap<>();
        List<Star> starList = starService.selectByUserId(id);
        for(Star star:starList){
            rantId2Value.put(star.getRantId(), star.getStarValue());
        }

        List<Rant> rantList = rantService.selectAll();
        List<RantItem> rantItemList = new ArrayList<>();
        for(Rant rant:rantList){
            RantItem rantItem = new RantItem();
            BeanUtils.copyProperties(rant, rantItem);
            String username = userService.selectByPrimaryKey(rant.getUserId()).getUserName();
            List<Comment> commentList = commentService.selectAllByRantId(rant.getRantId());
            rantItem.setUserName(username);
            //如果登录用户对此rant 赞过或者踩过
            if(rantId2Value.containsKey(rant.getRantId())){
                int value = rantId2Value.get(rant.getRantId());
                rantItem.setThumbFlag(value);
            }
            else{
                rantItem.setThumbFlag(0);
            }

            if(commentList==null) rantItem.setCommentsNum(0);
            else rantItem.setCommentsNum(commentList.size());
            rantItemList.add(rantItem);
        }
        Collections.reverse(rantItemList);//新的靠前
        return rantItemList;
    }


    @ResponseBody
    @RequestMapping(value = "/rant",method = {RequestMethod.GET})
    public DetailItem rant(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int rantId = Integer.valueOf(request.getParameter("rantId"));
        Rant rant = rantService.selectByPrimaryKey(rantId);
        DetailItem detailItem = new DetailItem();
        BeanUtils.copyProperties(rant, detailItem);
        User user = userService.selectByPrimaryKey(rant.getUserId());
        detailItem.setUserName(user.getUserName());
        detailItem.setUserAvatar(user.getUserAvatar());
        List<CommentItem> commentItemList =  new ArrayList<>();
        List<Comment> commentList = commentService.selectAllByRantId(rantId);
        for(Comment comment:commentList){
            CommentItem commentItem = new CommentItem();
            BeanUtils.copyProperties(comment, commentItem);
            commentItem.setUserName(userService.selectByPrimaryKey(comment.getUserId()).getUserName());
            commentItem.setUserAvatar(userService.selectByPrimaryKey(comment.getUserId()).getUserAvatar());
            commentItemList.add(commentItem);
        }
        detailItem.setCommentList(commentItemList);
        //设置当前浏览用户的赞踩状态 thumbValue
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        List<Star> starList = starService.selectByUserId(id);
        detailItem.setThumbValue(0);//by default
        for(Star star: starList){
            if(star.getRantId()==rantId && star.getStarValue()==1){
                detailItem.setThumbValue(1);
            }
            else if(star.getRantId()==rantId && star.getStarValue()==-1){
                detailItem.setThumbValue(-1);
            }
        }
        return detailItem;

    }

    @RequestMapping(value = "/postrant", method = {RequestMethod.POST})
    public void postrant(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter("token");
        String content = request.getParameter("content");
        String isHidden = request.getParameter("isHidden");

        int userId = -1;
        userId = tokenService.queryIdByToken(token);
        if(userId==-1){//没有查询到用户
            response.getWriter().print("0");
        }
        else{//找到用户
            User user = userService.selectByPrimaryKey(userId);
            Rant rant = new Rant();
            if(isHidden.equals("1")){//匿名
                rant.setRantHidden(1);
                rant.setRantAvatar("http://i4.buimg.com/567571/846a641dcc87882b.png");
            }
            else{//公开
                rant.setRantHidden(0);
                rant.setRantAvatar(user.getUserAvatar());
            }
            rant.setRantContent(content);
            rant.setRantDate(new Date());
            rant.setRantValue(0);
            rant.setUserId(user.getUserId());
            rantService.insert(rant);
            response.getWriter().print("1");
        }

    }

    @RequestMapping(value = "/thumbsUp",method = {RequestMethod.POST})
    public void thumbsUp(HttpServletRequest request){
        String token = request.getParameter("token");
        int rantId = Integer.valueOf(request.getParameter("rantId"));
        int userId = tokenService.queryIdByToken(token);
        List<Star> starList = starService.selectByRantId(rantId);//得到这篇文章的所有赞和踩

        for(Star star:starList){
            if(star.getUserId().equals(userId)&&star.getStarValue()==1){//已经赞过
                //取消赞
                Rant rant = rantService.selectByPrimaryKey(rantId);
                Integer value = rant.getRantValue();
                rant.setRantValue(value-1);
                rantService.updateByPrimaryKeySelective(rant);
                starService.deleteByPrimaryKey(star.getStarId());
                return;
            }
            else if(star.getUserId().equals(userId)&&star.getStarValue()==-1){//已经踩过
                //取消踩
                Rant rant = rantService.selectByPrimaryKey(rantId);
                Integer value = rant.getRantValue();
                rant.setRantValue(value+1);
                rantService.updateByPrimaryKeySelective(rant);
                starService.deleteByPrimaryKey(star.getStarId());
                return;
            }
        }
        //没有赞过也没有踩过
        Star star = new Star();
        star.setRantId(rantId);
        star.setUserId(userId);
        star.setStarValue(1);
        star.setStarRead(0);
        star.setStarDate(new Date());
        starService.insert(star);
        Rant rant = rantService.selectByPrimaryKey(rantId);
        rant.setRantValue(rant.getRantValue()+1);
        rantService.updateByPrimaryKeySelective(rant);
    }

    @RequestMapping(value = "/thumbsDown",method = {RequestMethod.POST})
    public void thumbsDown(HttpServletRequest request){
        String token = request.getParameter("token");
        int rantId = Integer.valueOf(request.getParameter("rantId"));
        int userId = tokenService.queryIdByToken(token);
        List<Star> starList = starService.selectByRantId(rantId);//得到这篇文章的所有赞和踩

        for(Star star:starList){
            if(star.getUserId().equals(userId)&&star.getStarValue()==1){//已经赞过
                //取消赞
                Rant rant = rantService.selectByPrimaryKey(rantId);
                Integer value = rant.getRantValue();
                rant.setRantValue(value-1);
                rantService.updateByPrimaryKeySelective(rant);
                starService.deleteByPrimaryKey(star.getStarId());
                return;
            }
            else if(star.getUserId().equals(userId)&&star.getStarValue()==-1){//已经踩过
                //取消踩
                Rant rant = rantService.selectByPrimaryKey(rantId);
                Integer value = rant.getRantValue();
                rant.setRantValue(value+1);
                rantService.updateByPrimaryKeySelective(rant);
                starService.deleteByPrimaryKey(star.getStarId());
                return;
            }
        }
        //没有赞过也没有踩过
        Star star = new Star();
        star.setRantId(rantId);
        star.setUserId(userId);
        star.setStarValue(-1);
        star.setStarRead(0);
        star.setStarDate(new Date());
        starService.insert(star);
        Rant rant = rantService.selectByPrimaryKey(rantId);
        rant.setRantValue(rant.getRantValue()-1);
        rantService.updateByPrimaryKeySelective(rant);
    }

    @ResponseBody
    @RequestMapping(value = "/getCmtNotify",method = {RequestMethod.GET})
    public List<CmtNotifyItem> getCmtNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        List<Comment> allComments = commentService.selectAll();
        List<Comment> replyComments = new ArrayList<>();

        for(Comment comment:allComments){
            Rant rant = rantService.selectByPrimaryKey(comment.getRantId());
            if(rant==null) continue;//删除某个Rant后，其评论可能有残留
            if(comment.getUserId()!=id && rant.getUserId()==id){
                replyComments.add(comment);
            }
        }

        List<CmtNotifyItem> cmtNotifyItems = new ArrayList<>();
        for(Comment comment:replyComments){
            CmtNotifyItem cmtNotifyItem = new CmtNotifyItem();
            BeanUtils.copyProperties(comment, cmtNotifyItem);
            User user = userService.selectByPrimaryKey(comment.getUserId());
            Rant rant = rantService.selectByPrimaryKey(comment.getRantId());
            cmtNotifyItem.setUserAvatar(user.getUserAvatar());
            cmtNotifyItem.setUserName(user.getUserName());
            cmtNotifyItem.setRantContent(rant.getRantContent());
            cmtNotifyItems.add(cmtNotifyItem);
        }

        //Collections.reverse(cmtNotifyItems);
        //这里返回的就是从近到远的顺序， 不知道为什么。
        return cmtNotifyItems;

    }

    @RequestMapping(value = "/getCmtNotifyCnt",method = {RequestMethod.GET})
    public void getCmtNotifyNew(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        List<Rant> rantList = rantService.selectByUserId(id);
        List<Comment> allComments = new ArrayList<>();
        for(Rant rant:rantList){
            List<Comment> comments = commentService.selectAllByRantId(rant.getRantId());
            List<Comment> commentsFromOthers = new ArrayList<>();
            for(Comment comment:comments){
                if(comment.getUserId()!=id)commentsFromOthers.add(comment);
            }
            allComments.addAll(commentsFromOthers);
        }

        List<CmtNotifyItem> cmtNotifyItems = new ArrayList<>();
        for(Comment comment:allComments){
                CmtNotifyItem cmtNotifyItem = new CmtNotifyItem();
                BeanUtils.copyProperties(comment, cmtNotifyItem);
                User user = userService.selectByPrimaryKey(comment.getUserId());
                Rant rant = rantService.selectByPrimaryKey(comment.getRantId());
                cmtNotifyItem.setUserAvatar(user.getUserAvatar());
                cmtNotifyItem.setUserName(user.getUserName());
                cmtNotifyItem.setRantContent(rant.getRantContent());
                cmtNotifyItems.add(cmtNotifyItem);
        }

        response.getWriter().print(cmtNotifyItems.size());
    }

    @ResponseBody
    @RequestMapping(value = "/getStarNotify",method = {RequestMethod.GET})
    public List<StarNotifyItem> getStarNotify(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        List<Star> allStar = starService.selectAll();
        List<Star> replyStar = new ArrayList<>();

        for(Star star:allStar){
            Rant rant = rantService.selectByPrimaryKey(star.getRantId());
            if(rant==null) continue;
            if(star.getUserId()!=id && rant.getUserId()==id){
                replyStar.add(star);
            }
        }

        List<StarNotifyItem> starNotifyItems = new ArrayList<>();
        for(Star star:replyStar){
            StarNotifyItem starNotifyItem = new StarNotifyItem();
            BeanUtils.copyProperties(star, starNotifyItem);
            User user = userService.selectByPrimaryKey(star.getUserId());
            Rant rant = rantService.selectByPrimaryKey(star.getRantId());
            starNotifyItem.setUserAvatar(user.getUserAvatar());
            starNotifyItem.setUserName(user.getUserName());
            starNotifyItem.setRantContent(rant.getRantContent());
            starNotifyItems.add(starNotifyItem);
        }


        return starNotifyItems;
    }


    @RequestMapping(value = "/getStarNotifyCnt",method = {RequestMethod.GET})
    public void getStarNotifyNew(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        List<Rant> rantList = rantService.selectByUserId(id);
        List<Star> allStars = new ArrayList<>();
        for(Rant rant:rantList){
            List<Star> stars = starService.selectByRantId(rant.getRantId());
            List<Star> starsFromOthers = new ArrayList<>();
            for(Star star:stars){
                if(star.getUserId()!=id)starsFromOthers.add(star);
            }
            allStars.addAll(starsFromOthers);
        }

        List<StarNotifyItem> starNotifyItems = new ArrayList<>();
        for(Star star:allStars){
                StarNotifyItem starNotifyItem = new StarNotifyItem();
                BeanUtils.copyProperties(star, starNotifyItem);
                User user = userService.selectByPrimaryKey(star.getUserId());
                Rant rant = rantService.selectByPrimaryKey(star.getRantId());
                starNotifyItem.setUserAvatar(user.getUserAvatar());
                starNotifyItem.setUserName(user.getUserName());
                starNotifyItem.setRantContent(rant.getRantContent());
                starNotifyItems.add(starNotifyItem);
        }


        response.getWriter().print(starNotifyItems.size());
    }


    @RequestMapping(value = "/sendComment",method = {RequestMethod.POST})
    public void sendComment(HttpServletRequest request, HttpServletResponse response){
        String token = request.getParameter("token");
        String content = request.getParameter("content");
        int rantId = Integer.parseInt(request.getParameter("rantId"));
        int userId = tokenService.queryIdByToken(token);

        User user = userService.selectByPrimaryKey(userId);
        Comment comment = new Comment();
        comment.setCommentContent(content);
        comment.setUserId(user.getUserId());
        comment.setCommentDate(new Date());
        comment.setCommentHidden(0);
        comment.setRantId(rantId);
        comment.setCommentValue(0);
        comment.setCommentRead(0);
        commentService.insert(comment);
    }


    @ResponseBody
    @RequestMapping(value = "/userInfo",method = {RequestMethod.GET})
    public User userInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        return userService.selectByPrimaryKey(id);
    }


    @ResponseBody
    @RequestMapping(value = "/userProfile",method = {RequestMethod.GET})
    public UserItem userProfile(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);

        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userService.selectByPrimaryKey(userId);
        UserItem userItem = new UserItem();
        BeanUtils.copyProperties(user, userItem);

        HashMap<Integer, Integer> rantId2Value = new HashMap<>();
        List<Star> starList = starService.selectByUserId(id);
        for(Star star:starList){
            rantId2Value.put(star.getRantId(), star.getStarValue());
        }

        List<Rant> rantList = rantService.selectByUserId(userId);
        List<RantItem> rantItemList = new ArrayList<>();
        for(Rant rant:rantList){
            RantItem rantItem = new RantItem();
            BeanUtils.copyProperties(rant, rantItem);
            String username = userService.selectByPrimaryKey(rant.getUserId()).getUserName();
            List<Comment> commentList = commentService.selectAllByRantId(rant.getRantId());
            rantItem.setUserName(username);
            //如果登录用户对此rant 赞过或者踩过
            if(rantId2Value.containsKey(rant.getRantId())){
                int value = rantId2Value.get(rant.getRantId());
                rantItem.setThumbFlag(value);
            }
            else{
                rantItem.setThumbFlag(0);
            }

            if(commentList==null) rantItem.setCommentsNum(0);
            else rantItem.setCommentsNum(commentList.size());
            rantItemList.add(rantItem);
        }
        userItem.setUserRants(rantItemList);
        if(id==userId) userItem.setMyself(true);
        else userItem.setMyself(false);
        int tot=0;
        for(Rant rant:rantList) tot+=rant.getRantValue();
        userItem.setUserValue(tot);
        return userItem;
    }

    @RequestMapping(value = "/editInfo", method = {RequestMethod.POST})
    public void editInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
        int userId = Integer.parseInt(request.getParameter("userId"));
        String bio = request.getParameter("bio");
        String location = request.getParameter("location");
        User user = userService.selectByPrimaryKey(userId);
        user.setUserBio(bio);
        user.setUserLocation(location);
        response.getWriter().print(userService.updateByPrimaryKeySelective(user));

    }

    @RequestMapping(value = "/getQiniuUploadToken", method = {RequestMethod.GET})
    public void getQiniuUploadToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //...生成上传凭证，然后准备上传
        String accessKey = "AOk-D-W-Dh1JL-gaWoklm5kaWo7FvNoi3rKZOa_i";
        String secretKey = "PTgPSl9LRS0O2-JHu1FxG9JNVfKOhwaTSNZJy5LL";
        String bucket = "rantimg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        response.getWriter().print(upToken);
    }

    @RequestMapping(value = "/changeAvatar", method = {RequestMethod.POST})
    public void changeAvatar(HttpServletRequest request){
        String token = request.getParameter("token");
        int userId = Integer.parseInt(request.getParameter("userId"));
        int id = tokenService.queryIdByToken(token);
        String img = request.getParameter("img");
        if(userId==id){
            User user = userService.selectByPrimaryKey(userId);
            user.setUserAvatar(img);
            userService.updateByPrimaryKeySelective(user);
        }
        //批量修改rant头像

    }

    @RequestMapping(value = "/setRead",method = RequestMethod.GET)
    public void setRead(HttpServletRequest request, HttpServletResponse response)throws IOException{
        /**
         * 存在flag参数，flag=0表示标记评论，flag=1表示标记star
         */
        int flag = Integer.parseInt(request.getParameter("flag"));
        int id = Integer.parseInt(request.getParameter("id"));
        if(flag==0){
            Comment comment = commentService.selectByPrimaryKey(id);
            comment.setCommentRead(1);
            commentService.updateByPrimaryKeySelective(comment);
            response.getWriter().print("cmt:success");
        }
        else{
            Star star = starService.selectByPrimaryKey(id);
            star.setStarRead(1);
            starService.updateByPrimaryKey(star);
            response.getWriter().print("star:success");
        }
    }

    @RequestMapping(value = "/setReadAll",method = RequestMethod.GET)
    public void setReadAll(HttpServletRequest request, HttpServletResponse response)throws IOException{

    }



    @ResponseBody
    @RequestMapping(value = "/myRant",method = {RequestMethod.GET})
    public List<RantItem> myRant(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        List<Rant> rantList = rantService.selectByUserId(id);
        List<RantItem> rantItemList = new ArrayList<>();

        HashMap<Integer, Integer> rantId2Value = new HashMap<>();
        List<Star> starList = starService.selectByUserId(id);
        for(Star star:starList){
            rantId2Value.put(star.getRantId(), star.getStarValue());
        }


        for(Rant rant: rantList){
            RantItem rantItem = new RantItem();
            BeanUtils.copyProperties(rant,rantItem);
            rantItem.setUserName(userService.selectByPrimaryKey(id).getUserName());
            List<Comment> commentList = commentService.selectAllByRantId(rant.getRantId());
            //如果登录用户对此rant 赞过或者踩过
            if(rantId2Value.containsKey(rant.getRantId())){
                int value = rantId2Value.get(rant.getRantId());
                rantItem.setThumbFlag(value);
            }
            else{
                rantItem.setThumbFlag(0);
            }

            if(commentList==null) rantItem.setCommentsNum(0);
            else rantItem.setCommentsNum(commentList.size());
            rantItemList.add(rantItem);
        }

        Collections.reverse(rantItemList);
        return rantItemList;
    }

    @ResponseBody
    @RequestMapping(value = "/myUp", method = {RequestMethod.GET})
    public List<RantItem> myUp(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        List<Star> starList = starService.selectByUserId(id);
        List<Rant> rantList = new ArrayList<>();
        for(Star star:starList){
            if(star.getStarValue()==1){
                rantList.add(rantService.selectByPrimaryKey(star.getRantId()));
            }
        }

        List<RantItem> rantItemList = new ArrayList<>();

        for(Rant rant: rantList){
            RantItem rantItem = new RantItem();
            BeanUtils.copyProperties(rant,rantItem);
            rantItem.setUserName(userService.selectByPrimaryKey(id).getUserName());
            List<Comment> commentList = commentService.selectAllByRantId(rant.getRantId());
            rantItem.setThumbFlag(1);
            if(commentList==null) rantItem.setCommentsNum(0);
            else rantItem.setCommentsNum(commentList.size());
            rantItemList.add(rantItem);
        }

        Collections.reverse(rantItemList);
        return rantItemList;

    }

    @ResponseBody
    @RequestMapping(value = "/myDown", method = {RequestMethod.GET})
    public List<RantItem> myDown(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        List<Star> starList = starService.selectByUserId(id);
        List<Rant> rantList = new ArrayList<>();
        for(Star star:starList){
            if(star.getStarValue()==-1){
                rantList.add(rantService.selectByPrimaryKey(star.getRantId()));
            }
        }

        List<RantItem> rantItemList = new ArrayList<>();

        for(Rant rant: rantList){
            RantItem rantItem = new RantItem();
            BeanUtils.copyProperties(rant,rantItem);
            rantItem.setUserName(userService.selectByPrimaryKey(id).getUserName());
            List<Comment> commentList = commentService.selectAllByRantId(rant.getRantId());
            rantItem.setThumbFlag(-1);
            if(commentList==null) rantItem.setCommentsNum(0);
            else rantItem.setCommentsNum(commentList.size());
            rantItemList.add(rantItem);
        }

        Collections.reverse(rantItemList);
        return rantItemList;

    }

    @RequestMapping(value = "/myComment", method = {RequestMethod.GET})
    public List<CmtNotifyItem> myComment(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        List<Comment> commentList = commentService.selectAllByUserId(id);

        //这里借用CmtNotifyItem
        List<CmtNotifyItem> cmtNotifyItems = new ArrayList<>();
        for(Comment comment:commentList){
            CmtNotifyItem cmtNotifyItem = new CmtNotifyItem();
            BeanUtils.copyProperties(comment, cmtNotifyItem);
            User user = userService.selectByPrimaryKey(id);
            Rant rant = rantService.selectByPrimaryKey(comment.getRantId());
            cmtNotifyItem.setUserAvatar(user.getUserAvatar());
            cmtNotifyItem.setUserName(user.getUserName());
            cmtNotifyItem.setRantContent(rant.getRantContent());
            cmtNotifyItems.add(cmtNotifyItem);
        }

        //Collections.reverse(cmtNotifyItems);
        //这里返回的就是从近到远的顺序， 不知道为什么。
        return cmtNotifyItems;

    }

    @RequestMapping(value = "/deleteRant", method = {RequestMethod.GET})
    public void deleteRant(HttpServletRequest request) throws IOException{
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        int rantId = Integer.parseInt(request.getParameter("rantId"));
        //简单校验
        Rant rant = rantService.selectByPrimaryKey(rantId);
        if(id==rant.getUserId()){
            rantService.deleteByPrimaryKey(rantId);
        }

    }

    @RequestMapping(value = "/deleteComment", method = {RequestMethod.GET})
    public void deleteComment(HttpServletRequest request) throws IOException{
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        int commentId = Integer.parseInt(request.getParameter("commentId"));
        //简单校验
        Comment comment = commentService.selectByPrimaryKey(commentId);
        if(id==comment.getUserId()){
            commentService.deleteByPrimaryKey(commentId);
        }
    }

    @RequestMapping(value = "/cancelThumb", method = {RequestMethod.GET})
    public void cancelThumb(HttpServletRequest request)throws IOException{
        String token = request.getParameter("token");
        int id = tokenService.queryIdByToken(token);
        int rantId = Integer.parseInt(request.getParameter("rantId"));
        List<Star> starList = starService.selectByUserId(id);
        for(Star star: starList){
            if(star.getRantId()==rantId){
                starService.deleteByPrimaryKey(star.getStarId());
            }
        }
    }


    // TODO: 2017/5/1 可能需要包装
    @ResponseBody
    @RequestMapping("/allUsers")
    public List<User> allUsers(HttpServletResponse response) throws IOException {
        return userService.selectAll();
    }

    // TODO: 2017/5/1 可能需要包装
    @ResponseBody
    @RequestMapping("/rant")
    public List<Rant> rant(HttpServletResponse response, Integer userId) throws IOException {
        return rantService.selectByUserId(userId);
    }

    // TODO: 2017/5/1 可能需要包装
    @ResponseBody
    @RequestMapping("/recentHotRants")
    public List<Rant> recentHotRants(HttpServletResponse response) throws IOException {
        List<Rant> rantList = rantService.selectAll();
        //近期热门主题
        Date now = new Date();
        long nowLong = now.getTime();
        long sub = 3*24*60*60*1000;//三天时间
        int commentThreshold = 1;//评论数阈值
        List<Rant> recentHotRantList = new ArrayList<>();
        for(Rant rant:rantList){
            int commentCnt = commentService.selectAllByRantId(rant.getRantId()).size();
            if(commentCnt>=commentThreshold&&Math.abs(now.getTime()-rant.getRantDate().getTime())<=sub){
                recentHotRantList.add(rant);
            }
        }
        return recentHotRantList;
    }

    // TODO: 2017/5/1 可能需要包装
    @ResponseBody
    @RequestMapping("/valuableRants")
    public List<Rant> valuableRants(HttpServletResponse response) throws IOException {

        List<Rant> rantList = rantService.selectAll();
        //精华主题
        List<Rant> starRantList = new ArrayList<>();
        int valueThreshold = 30;//价值阈值
        for(Rant rant:rantList){
            if(rant.getRantValue()>=valueThreshold){
                starRantList.add(rant);
            }
        }
        return starRantList;
    }



    @RequestMapping(value = "/reg",method = {RequestMethod.POST})
    public void reg(HttpServletRequest request, HttpServletResponse response)throws IOException{

        //默认头像组
        List<String> defaultAvatarList = new ArrayList<>();
        defaultAvatarList.add("http://i2.muimg.com/567571/558499d1ed36df1e.jpg");//orange catty
        defaultAvatarList.add("http://i2.muimg.com/567571/852c22977e6002d0.jpg");//blue catty
        defaultAvatarList.add("http://i2.muimg.com/567571/e050cd026ff561dd.jpg");//red catty


        User user = new User();
        user.setUserName(request.getParameter("username"));
        user.setUserPassword(request.getParameter("password"));

        //默认头像
        user.setUserAvatar(defaultAvatarList.get(((int)(Math.random()*10))%3));

        //设置角色
        user.setUserRole(3);
        user.setUserValue(0);
        user.setUserBio("什么也没留下");
        user.setUserLocation("未知");

        //校验
        User v = userService.selectByUserName(user.getUserName());
        if(v==null){
            userService.insert(user);
            int id = userService.selectByUserName(user.getUserName()).getUserId();
            String deviceMd5 = request.getParameter("device");
            String date = new Date().toString();
            String tokenString = deviceMd5+md5(date);
            Token token = new Token();
            token.setUserId(id);
            token.setUserToken(tokenString);
            tokenService.create(token);

            response.getWriter().print(tokenString);

        }
        else{//用户名已存在
            response.getWriter().print("exist");
        }
    }

    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    public void login(HttpServletRequest request, HttpServletResponse response)throws IOException {
        String username =  request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.selectByUserName(username);
        //密码正确
        if(user!=null && password.equals(user.getUserPassword())){
            String deviceMd5 = request.getParameter("device");
            String date = new Date().toString();
            String tokenString = deviceMd5+md5(date);

            //删除之前的令牌
            tokenService.deleteById(user.getUserId());
            //新增令牌
            Token token = new Token();
            token.setUserId(user.getUserId());
            token.setUserToken(tokenString);
            tokenService.create(token);
            response.getWriter().print(tokenString);
        }
        else{
            response.getWriter().print("wrong");
        }


    }


    @RequestMapping(value = "/logout",method = {RequestMethod.POST})
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String tokenString = request.getParameter("token");
        tokenService.deleteByToken(tokenString);
    }


    public static String md5(String str){
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }



}