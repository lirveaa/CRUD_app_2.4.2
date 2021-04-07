package org.java.web.controller;

import org.java.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.java.web.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @GetMapping(value = {"infoCall", "/"})
    public ModelAndView index(Principal principal, ModelAndView modelAndView) {
        User user = userService.getUser(principal.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/user/info");
        return modelAndView;
    }

}
