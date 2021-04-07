package org.java.web.controller;

import org.java.web.model.Role;
import org.java.web.model.User;
import org.java.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class StartController {

    private static boolean isInit = false;

    @Autowired
    private UserService userService;

    @GetMapping("hello")
    public String printWelcome(ModelMap model, Principal principal) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello, " + principal.getName() + "!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);

        if (isInit) {
            insertDataToDB();
            isInit = false;
        }
        return "hello";
    }

    private void insertDataToDB() {
        System.out.println("\nInserting data ....");
        Role roleUser = new Role(1L, "ROLE_USER");
        Role roleAdmin = new Role(2L, "ROLE_ADMIN");
        Set<Role> bothSet = new HashSet<Role>();
        bothSet.add(roleUser);
        bothSet.add(roleAdmin);
        Set<Role>  admSet = new HashSet<>();
        admSet.add(roleAdmin);
        Set<Role> userSet = new HashSet<>();
        userSet.add(roleUser);

        User admin1 = new User("admin", "admin", 1997, admSet);
        User user_1 = new User("user1", "user", 1990, userSet);
        User user_2 = new User("Boris1", "boss", 2000, bothSet);
        User admin_2 = new User("Botya", "johanson", 1999, admSet);
        User user_3 = new User("Luri", "clack", 2001, bothSet);

        userService.createNewUser(admin1);
        userService.createNewUser(user_1);
        userService.createNewUser(user_2);
        userService.createNewUser(admin_2);
        userService.createNewUser(user_3);
    }
}
