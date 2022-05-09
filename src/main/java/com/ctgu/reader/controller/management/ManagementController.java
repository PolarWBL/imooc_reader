package com.ctgu.reader.controller.management;

import com.ctgu.reader.entity.User;
import com.ctgu.reader.service.UserService;
import com.ctgu.reader.service.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Boliang Weng
 */
@Controller
@RequestMapping("management")
public class ManagementController {
    @Resource
    private UserService userService;

    @GetMapping("index.html")
    public ModelAndView showIndex() {
        return new ModelAndView("management/index");
    }

    @GetMapping("login.html")
    public ModelAndView showLogin() {
        return new ModelAndView("management/login");
    }

    @PostMapping("check_login")
    @ResponseBody
    public Map<String, Object> checkLogin(String username, String password, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.checkLogin(username, password);
            session.setAttribute("loginUser", user);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

    @GetMapping("logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("/management/login");
    }
}
