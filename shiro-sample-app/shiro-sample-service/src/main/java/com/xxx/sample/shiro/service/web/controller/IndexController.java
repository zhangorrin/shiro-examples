package com.xxx.sample.shiro.service.web.controller;

import com.xxx.sample.shiro.service.Constants;
import com.xxx.sample.shiro.service.entity.Resource;
import com.xxx.sample.shiro.service.remote.User;
import com.xxx.sample.shiro.service.service.AuthorizationService;
import com.xxx.sample.shiro.service.service.ResourceService;
import com.xxx.sample.shiro.service.web.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Controller
public class IndexController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private AuthorizationService authorizationService;

    @RequestMapping("/")
    public String index(@CurrentUser User loginUser, Model model) {
        Set<String> permissions = authorizationService.findPermissions(Constants.SERVER_APP_KEY, loginUser.getUsername());
        List<Resource> menus = resourceService.findMenus(permissions);
        model.addAttribute("menus", menus);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }


}
