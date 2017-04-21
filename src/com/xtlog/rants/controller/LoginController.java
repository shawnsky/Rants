package com.xtlog.rants.controller;

import com.xtlog.rants.pojo.User;
import com.xtlog.rants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/17.
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    //登录
    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    public String loginSubmit(HttpSession session, String username, String password){
        User user = userService.selectByUserName(username);
        if(user==null){
            return "redirect:/home.action";
        }
        else if(!user.getUserPassword().equals(password)){
            return "redirect:/home.action";
        }
        //存在用户，且密码正确
        session.setAttribute("username", username);
        return "redirect:/home.action";
    }


    //注册
    @RequestMapping(value = "/register",method = {RequestMethod.POST})
    public String register(HttpSession session, String username, String password){
        //默认头像组
        List<String> defaultAvatarList = new ArrayList<>();
        defaultAvatarList.add("http://i2.muimg.com/567571/558499d1ed36df1e.jpg");//orange catty
        defaultAvatarList.add("http://i2.muimg.com/567571/852c22977e6002d0.jpg");//blue catty
        defaultAvatarList.add("http://i2.muimg.com/567571/e050cd026ff561dd.jpg");//red catty

        User user = new User();
        user.setUserName(username);
        user.setUserPassword(password);
        //默认头像
        user.setUserAvatar(defaultAvatarList.get(((int)(Math.random()*10))%3));

        //设置角色
        user.setUserRole(3);
        user.setUserValue(0);
        user.setUserBio("什么也没留下");
        user.setUserLocation("未知");
        userService.insert(user);
        session.setAttribute("username", username);
        return "redirect:/home.action";
    }


    //登出
    @RequestMapping("/logout")
    public String logoutSubmit(HttpSession session){
        session.invalidate();
        return "redirect:/home.action";
    }
}
