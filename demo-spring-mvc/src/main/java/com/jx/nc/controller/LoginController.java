package com.jx.nc.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class LoginController extends BaseController {

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    @ResponseBody
    public Map ajaxlogin(@RequestParam String account, @RequestParam String passwd) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, passwd);
        subject.login(token);
        subject.isAuthenticated();
        return success();
    }

    /**
     * 退出系统进入登录页面
     *
     * @return
     */
    @RequestMapping("logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }

    /**
     * 退出系统进入登录页面
     *
     * @return
     */
    @RequestMapping("defaultShiroError")
    public String defaultShiroError() {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
        return "shiroerror";
    }
}
