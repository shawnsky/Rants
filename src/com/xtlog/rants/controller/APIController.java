package com.xtlog.rants.controller;

import com.google.gson.Gson;
import com.xtlog.rants.pojo.Rant;
import com.xtlog.rants.pojo.User;
import com.xtlog.rants.service.CommentService;
import com.xtlog.rants.service.RantService;
import com.xtlog.rants.service.StarService;
import com.xtlog.rants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

    private static final String tem1 = "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <title>JSON</title>\n" +
            "</head>\n" +
            "<body>";
    private static final String tem2 = "</body>\n" +
            "</html>";

    @RequestMapping("/allRants")
    public void allRants(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        List<Rant> rantList = rantService.selectAll();
        String json = gson.toJson(rantList);
        response.getWriter().print(tem1+json+tem2);
    }

    @RequestMapping("/allUsers")
    public void allUsers(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        List<User> userList = userService.selectAll();
        String json = gson.toJson(userList);
        response.getWriter().print(tem1+json+tem2);
    }

    @RequestMapping("/rant")
    public void rant(HttpServletResponse response, Integer userId) throws IOException {
        Gson gson = new Gson();
        List<Rant> rantList = rantService.selectByUserId(userId);
        String json = gson.toJson(rantList);
        response.getWriter().print(tem1+json+tem2);
    }

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



}
