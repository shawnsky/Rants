package com.xtlog.rants.controller;

import com.google.gson.Gson;
import com.sun.deploy.net.HttpResponse;
import com.xtlog.rants.pojo.Comment;
import com.xtlog.rants.pojo.Rant;
import com.xtlog.rants.pojo.Token;
import com.xtlog.rants.pojo.User;
import com.xtlog.rants.service.*;
import com.xtlog.rants.wrapper.CommentItem;
import com.xtlog.rants.wrapper.DetailItem;
import com.xtlog.rants.wrapper.RantItem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    private static final String tem1 = "<html><head><meta charset=\"utf-8\"></head><body><pre style=\"word-wrap: break-word; white-space: pre-wrap;\">";
    private static final String tem2 = "</pre></body></html>";


    @RequestMapping(value = "/allRants", method = {RequestMethod.GET})
    public void allRants(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        List<Rant> rantList = rantService.selectAll();
        List<RantItem> rantItemList = new ArrayList<>();
        for(Rant rant:rantList){
            RantItem rantItem = new RantItem();
            BeanUtils.copyProperties(rant, rantItem);
            String username = userService.selectByPrimaryKey(rant.getUserId()).getUserName();
            List<Comment> commentList = commentService.selectAllByRantId(rant.getRantId());
            rantItem.setUserName(username);
            if(commentList==null) rantItem.setCommentsNum(0);
            else rantItem.setCommentsNum(commentList.size());
            rantItemList.add(rantItem);
        }
        Collections.reverse(rantItemList);//反向，新的靠前。
        String json = gson.toJson(rantItemList);
        response.getWriter().print(json);
    }

    @RequestMapping(value = "/rant",method = {RequestMethod.GET})
    public void rant(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int rantId = Integer.valueOf(request.getParameter("rantId"));
        Rant rant = rantService.selectByPrimaryKey(rantId);
        DetailItem detailItem = new DetailItem();
        BeanUtils.copyProperties(rant, detailItem);
        detailItem.setUserName(userService.selectByPrimaryKey(rant.getUserId()).getUserName());
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

        Gson gson = new Gson();
        response.getWriter().print(gson.toJson(detailItem));

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

    // TODO: 2017/5/1 可能需要包装  
    @RequestMapping("/allUsers")
    public void allUsers(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        List<User> userList = userService.selectAll();
        String json = gson.toJson(userList);
        response.getWriter().print(tem1+json+tem2);
    }

    // TODO: 2017/5/1 可能需要包装 
    @RequestMapping("/rant")
    public void rant(HttpServletResponse response, Integer userId) throws IOException {
        Gson gson = new Gson();
        List<Rant> rantList = rantService.selectByUserId(userId);
        String json = gson.toJson(rantList);
        response.getWriter().print(tem1+json+tem2);
    }

    // TODO: 2017/5/1 可能需要包装 
    @RequestMapping("/recentHotRants")
    public void recentHotRants(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
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
        String json = gson.toJson(recentHotRantList);
        response.getWriter().print(tem1+json+tem2);
    }

    // TODO: 2017/5/1 可能需要包装 
    @RequestMapping("/valuableRants")
    public void valuableRants(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        List<Rant> rantList = rantService.selectAll();
        //精华主题
        List<Rant> starRantList = new ArrayList<>();
        int valueThreshold = 30;//价值阈值
        for(Rant rant:rantList){
            if(rant.getRantValue()>=valueThreshold){
                starRantList.add(rant);
            }
        }
        String json = gson.toJson(starRantList);
        response.getWriter().print(tem1+json+tem2);
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
