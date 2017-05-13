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

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/4/17.
 */
@Controller
public class RantController {
    @Autowired
    private RantService rantService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private StarService starService;

    @RequestMapping(value = "/rant",method = {RequestMethod.GET})
    public String rant(Model model,Integer rantId){
        Rant rant = rantService.selectByPrimaryKey(rantId);
        User user = userService.selectByPrimaryKey(rant.getUserId());
        List<Comment> commentList = commentService.selectAllByRantId(rantId);
        HashMap<Integer, String> userId2userNameMap = new HashMap<>();
        for(Comment c:commentList){
            userId2userNameMap.put(c.getUserId(),userService.selectByPrimaryKey(c.getUserId()).getUserName());
        }
        model.addAttribute("userId2userNameMap",userId2userNameMap);
        model.addAttribute("rant",rant);
        model.addAttribute("user",user);
        model.addAttribute("commentList", commentList);
        return "rantDetail";
    }

    @RequestMapping(value = "/allRant",method = {RequestMethod.GET})
    public String allRant(Model model){
        model.addAttribute("rantList", rantService.selectAll());
        return "rantList";
    }

    @RequestMapping(value = "/deleteRant",method = {RequestMethod.POST})
    public String deleteRant(Integer rantId){
        rantService.deleteByPrimaryKey(rantId);
        //删除这个rant的评论
        List<Comment> commentList = commentService.selectAllByRantId(rantId);
        for(Comment comment:commentList){
            commentService.deleteByPrimaryKey(comment.getCommentId());
        }
        return "redirect:/home.action";
    }

    @RequestMapping(value = "/deleteComment",method = {RequestMethod.POST})
    public String deleteComment(Integer commentId){
        commentService.deleteByPrimaryKey(commentId);
        return "redirect:/home.action";
    }

    @RequestMapping(value = "/sendComment",method = {RequestMethod.POST})
    public String sendComment(String newCommentContent, Integer rantId, HttpSession session){
        User user = userService.selectByUserName((String) session.getAttribute("username"));
        Comment comment = new Comment();
        comment.setCommentContent(newCommentContent);
        comment.setUserId(user.getUserId());
        comment.setCommentDate(new Date());
        comment.setCommentHidden(0);
        comment.setRantId(rantId);
        comment.setCommentValue(0);
        comment.setCommentRead(0);
        commentService.insert(comment);
        return "redirect:/rant.action?rantId="+rantId;
    }

    @RequestMapping(value = "/thumbsUp",method = {RequestMethod.POST})
    public String thumbsUp(HttpSession session, Integer rantId){
        //已经登录
        Integer userId = userService.selectByUserName((String) session.getAttribute("username")).getUserId();//得到当前用户id
        List<Star> starList = starService.selectByRantId(rantId);//得到这篇文章的所有赞和踩

        for(Star star:starList){
            if(star.getUserId().equals(userId)&&star.getStarValue()==1){//已经赞过
                //取消赞
                Rant rant = rantService.selectByPrimaryKey(rantId);
                Integer value = rant.getRantValue();
                rant.setRantValue(value-1);
                rantService.updateByPrimaryKeySelective(rant);
                starService.deleteByPrimaryKey(star.getStarId());
                return "redirect:/rant.action?rantId="+rantId;
            }
            else if(star.getUserId().equals(userId)&&star.getStarValue()==-1){//已经踩过
                //取消踩
                Rant rant = rantService.selectByPrimaryKey(rantId);
                Integer value = rant.getRantValue();
                rant.setRantValue(value+1);
                rantService.updateByPrimaryKeySelective(rant);
                starService.deleteByPrimaryKey(star.getStarId());
                return "redirect:/rant.action?rantId="+rantId;
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
        return "redirect:/rant.action?rantId="+rantId;
    }


    @RequestMapping(value = "/thumbsDown",method = {RequestMethod.POST})
    public String thumbsDown(HttpSession session, Integer rantId){
        //已经登录
        Integer userId = userService.selectByUserName((String) session.getAttribute("username")).getUserId();//得到当前用户id
        List<Star> starList = starService.selectByRantId(rantId);//得到这篇文章的所有赞和踩

        for(Star star:starList){
            if(star.getUserId().equals(userId)&&star.getStarValue()==1){//已经赞过
                //取消赞
                Rant rant = rantService.selectByPrimaryKey(rantId);
                Integer value = rant.getRantValue();
                rant.setRantValue(value-1);
                rantService.updateByPrimaryKeySelective(rant);
                starService.deleteByPrimaryKey(star.getStarId());
                return "redirect:/rant.action?rantId="+rantId;
            }
            else if(star.getUserId().equals(userId)&&star.getStarValue()==-1){//已经踩过
                //取消踩
                Rant rant = rantService.selectByPrimaryKey(rantId);
                Integer value = rant.getRantValue();
                rant.setRantValue(value+1);
                rantService.updateByPrimaryKeySelective(rant);
                starService.deleteByPrimaryKey(star.getStarId());
                return "redirect:/rant.action?rantId="+rantId;
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
        return "redirect:/rant.action?rantId="+rantId;

    }
}
