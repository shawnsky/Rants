package com.xtlog.rants.controller;

import com.xtlog.rants.pojo.Comment;
import com.xtlog.rants.pojo.Rant;
import com.xtlog.rants.pojo.Star;
import com.xtlog.rants.pojo.User;
import com.xtlog.rants.service.CommentService;
import com.xtlog.rants.service.RantService;
import com.xtlog.rants.service.StarService;
import com.xtlog.rants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/4/18.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RantService rantService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private StarService starService;

    @RequestMapping(value = "/user", method = {RequestMethod.GET})
    public String user(String userName, Model model, HttpSession session){
        User user = userService.selectByUserName(userName);


        //获取rant列表
        List<Rant> rantList = rantService.selectByUserId(user.getUserId());
        HashMap<Integer,Integer> rantId2ComCntMap = new HashMap<>();
        for(Rant rant:rantList){
            rantId2ComCntMap.put(rant.getRantId(),commentService.selectAllByRantId(rant.getRantId()).size());//评论数映射
        }


        //获取评论列表
        List<Comment> commentList = commentService.selectAllByUserId(user.getUserId());

        model.addAttribute("commentList", commentList);
        model.addAttribute("rantId2ComCntMap", rantId2ComCntMap);
        model.addAttribute("rantList", rantList);
        model.addAttribute("user", user);

        //获取我赞过的列表 获取我踩过的列表
        List<Rant> myThumbsDownRantList = new ArrayList<>();
        List<Rant> myThumbsUpRantList = new ArrayList<>();
        List<Star> starList = starService.selectByUserId(user.getUserId());
        for(Star star:starList){
            if(star.getStarValue()==1){
                myThumbsUpRantList.add(rantService.selectByPrimaryKey(star.getRantId()));
            }
            else if(star.getStarValue()==-1){
                myThumbsDownRantList.add(rantService.selectByPrimaryKey(star.getRantId()));
            }
        }
        model.addAttribute("myThumbsUpRantList", myThumbsUpRantList);
        model.addAttribute("myThumbsDownRantList", myThumbsDownRantList);


//        本人登录
        if(session.getAttribute("username")==null){
            return "userInfo";
        }
        else if(session.getAttribute("username").equals(userName)){
            return "myInfo";
        }
        else {
            return "userInfo";
        }
    }

    //修改信息
    @RequestMapping(value = "/editInfo", method = {RequestMethod.POST})
    public String editInfo(Integer userId, String location, String bio, RedirectAttributes attributes) throws UnsupportedEncodingException {
        User user = userService.selectByPrimaryKey(userId);
        user.setUserLocation(location);
        user.setUserBio(bio);

        attributes.addAttribute("userName", user.getUserName());

        userService.updateByPrimaryKeySelective(user);
        return "redirect:/user.action";
    }
    //修改密码
    @RequestMapping(value = "/editPwd", method = {RequestMethod.POST})
    public String editPwd(Integer userId, String password, RedirectAttributes attributes){
        User user = userService.selectByPrimaryKey(userId);
        user.setUserPassword(password);
        attributes.addAttribute("userName", user.getUserName());
        userService.updateByPrimaryKeySelective(user);
        return "redirect:/user.action";
    }
}
