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
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.*;

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
        Collections.reverse(rantList);//反向输出列表，
        HashMap<Integer,String> userId2NameMap = new HashMap<>();
        HashMap<Integer,Integer> rantId2ComCntMap = new HashMap<>();


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
        //精华主题
        List<Rant> starRantList = new ArrayList<>();
        int valueThreshold = 30;//价值阈值
        for(Rant rant:rantList){
            if(rant.getRantValue()>=valueThreshold){
                starRantList.add(rant);
            }
        }

        //映射赋值
        for(Rant rant:rantList){
            userId2NameMap.put(rant.getUserId(),userService.selectByPrimaryKey(rant.getUserId()).getUserName());//用户名映射
            rantId2ComCntMap.put(rant.getRantId(),commentService.selectAllByRantId(rant.getRantId()).size());//评论数映射
        }


        model.addAttribute("rantList", rantList);
        model.addAttribute("recentHotRantList", recentHotRantList);
        model.addAttribute("starRantList",starRantList);
        model.addAttribute("userId2NameMap",userId2NameMap);
        model.addAttribute("rantId2ComCntMap", rantId2ComCntMap);
        return "home";
    }

    @RequestMapping(value = "/sendRant", method = {RequestMethod.POST})
    public String sendRant(HttpSession session, String newRantContent, String newRantCheckBox){
        User user = userService.selectByUserName((String) session.getAttribute("username"));
        Rant rant = new Rant();

        if(newRantCheckBox != null){//匿名
            rant.setRantHidden(1);
            rant.setRantAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492506012487&di=1e73fe9d3757a6669ac7a026ea0e1455&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3D57e5bea30ffa513d51ff64da085d79cd%2F43a7d933c895d1436b21cf5c74f082025aaf077d.jpg");
        }
        else{//公开
            rant.setRantHidden(0);
            rant.setRantAvatar(user.getUserAvatar());
        }
        rant.setRantContent(newRantContent);
        rant.setRantDate(new Date());
        rant.setRantValue(0);
        rant.setUserId(user.getUserId());
        rantService.insert(rant);

        return "redirect:/home.action";
    }
}
