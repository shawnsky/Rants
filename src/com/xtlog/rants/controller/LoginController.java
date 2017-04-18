package com.xtlog.rants.controller;

import com.xtlog.rants.pojo.User;
import com.xtlog.rants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by admin on 2017/4/17.
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(){

        return "login";
    }

    @RequestMapping("/loginSubmit")
    public String loginSubmit(HttpSession session, String username, String password){
        User user = userService.selectByUserName(username);
        if(user==null){
            return "redirect:/login.action";
        }
        else if(!user.getUserPassword().equals(password)){
            return "redirect:/login.action";
        }
        //存在用户，且密码正确
        session.setAttribute("username", username);
        return "redirect:/home.action";
    }

    @RequestMapping("/logout")
    public String logoutSubmit(HttpSession session){
        session.invalidate();
        return "redirect:/home.action";
    }
}
