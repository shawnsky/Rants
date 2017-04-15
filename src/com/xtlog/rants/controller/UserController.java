package com.xtlog.rants.controller;

import com.xtlog.rants.pojo.User;
import com.xtlog.rants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by admin on 2017/4/16.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user",method = {RequestMethod.GET})
    public String user(){
        return "userInsert";
    }

    @RequestMapping(value = "/userSubmit",method = {RequestMethod.POST})
    public String userSubmit(String user_name){
        User user = new User();
        user.setUserName(user_name);
        userService.insert(user);
        return "userInsert";
    }
}
