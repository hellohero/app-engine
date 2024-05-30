package com.appengine.user.web;

import com.appengine.auth.annotation.AuthType;
import com.appengine.auth.annotation.BaseInfo;
import com.appengine.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Authors: sofn
 * Version: 1.0  Created at 2015-10-18 00:09.
 */
@Controller
@RequestMapping("/web")
public class WebUserController {

    @Resource
    private UserService userService;

    @BaseInfo(needAuth = AuthType.OPTION)
    @GetMapping(value = "register")
    public String register() {
        return "account/register";
    }

    @BaseInfo(needAuth = AuthType.OPTION)
    @GetMapping(value = "login")
    public String login() {
        return "account/login";
    }


}
