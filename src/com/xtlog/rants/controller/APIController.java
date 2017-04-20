package com.xtlog.rants.controller;

import com.google.gson.Gson;
import com.xtlog.rants.pojo.Rant;
import com.xtlog.rants.service.CommentService;
import com.xtlog.rants.service.RantService;
import com.xtlog.rants.service.StarService;
import com.xtlog.rants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
}
