package com.xtlog.rants.controller;

import com.xtlog.rants.pojo.Rant;
import com.xtlog.rants.service.CommentService;
import com.xtlog.rants.service.RantService;
import com.xtlog.rants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/4/16.
 */
@Controller
public class HomeController {
    @Autowired
    private RantService rantService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;


    @RequestMapping(value = "/home", method = {RequestMethod.GET})
    public String home(Model model){
        List<Rant> rantList = rantService.selectAll();
        model.addAttribute("rantList", rantList);
        HashMap<Integer,String> userId2NameMap = new HashMap<>();
        HashMap<Integer,Integer> rantId2ComCntMap = new HashMap<>();
        //rant列表里的用户
        for(Rant rant:rantList){
            userId2NameMap.put(rant.getUserId(),userService.selectByPrimaryKey(rant.getUserId()).getUserName());
            rantId2ComCntMap.put(rant.getUserId(),commentService.selectAllByRantId(rant.getRantId()).size());
        }
        model.addAttribute("userId2NameMap",userId2NameMap);
        model.addAttribute("rantId2ComCntMap", rantId2ComCntMap);
        return "home";
    }
}
