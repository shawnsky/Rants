package com.xtlog.rants.controller;

import com.xtlog.rants.pojo.Comment;
import com.xtlog.rants.pojo.Rant;
import com.xtlog.rants.pojo.User;
import com.xtlog.rants.service.CommentService;
import com.xtlog.rants.service.RantService;
import com.xtlog.rants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/rant")
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
}
