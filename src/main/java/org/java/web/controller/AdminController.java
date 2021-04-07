package org.java.web.controller;

import org.java.web.model.Role;
import org.java.web.model.User;
import org.java.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    public UserService userService;

    @GetMapping("/start")
    public String startAdmin(ModelMap modelMap) {
        List<User> userList = userService.usersList();
        modelMap.addAttribute("listUsers", userList);
        return "/admin/start";
    }

    @GetMapping(value = "/new")
    public ModelAndView addNewUserForm(ModelAndView modelAndView) {
        getNewModelAndView(modelAndView);
        modelAndView.addObject("isAdmin", false);
        modelAndView.addObject("isUser", true);
        modelAndView.setViewName("admin/add_new");
        return modelAndView;
    }

    @PostMapping("/new")
    public String newUser(@RequestParam(name="isAdmin", required = false) boolean isAdmin,
                          @RequestParam(name="isUser", required = false) boolean isUser,
                          @ModelAttribute User user) {
        Set<Role> rolesToAdd = new HashSet<>();
        if (isUser) {
            rolesToAdd.add(new Role(1L, "ROLE_USER"));
        }
        if (isAdmin) {
            rolesToAdd.add(new Role(2L, "ROLE_ADMIN"));
        }
        user.setRoles(rolesToAdd);
        userService.createNewUser(user);
        return "redirect:/admin/start";
    }

    @GetMapping("/edit")
    public ModelAndView editForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        ModelAndView mav = new ModelAndView("admin/update");
        User user = userService.readUser(id);
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam(name = "id", defaultValue = "1") long id,
                           @ModelAttribute User user) {
        Set<Role>  setOldRoles = userService.readUser(id).getRoles();
        user.setRoles(setOldRoles);
        userService.updateUser(user);
        return "redirect:/admin/start";
    }

    @GetMapping("/delete")
    public String deleteUserForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/start";
    }

    @GetMapping("/search")
    public String findUserByIdForm() {
        return "admin/search_form";
    }

    @PostMapping("/searchResult")
    public ModelAndView findUserResultForm(@RequestParam(name = "id", defaultValue = "1") long id,
                                           ModelAndView mav) {
        User user = userService.readUser(id);
        mav.setViewName("admin/search_result_form");
        mav.addObject("user", user);
        return mav;
    }

    private void getNewModelAndView(ModelAndView modelAndView) {
        User user = new User();
        user.setUsername("somebody");
        user.setPassword("password");
        System.out.println(user);
        List<Role> listRoles = userService.rolesList();
        modelAndView.addObject("roles", listRoles);
        modelAndView.addObject("user", user);
    }
}
