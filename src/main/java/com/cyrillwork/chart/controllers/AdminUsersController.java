package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.Role;
import com.cyrillwork.chart.User;
import com.cyrillwork.chart.repos.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class AdminUsersController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin_users")
    public  String admin_users(Model model)
    {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin_users.html";
    }

    @PostMapping("/add_user")
    public String add_user(  @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String role,
                             Model model)
    {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);

        if("root".equals(username)) {
            u.setRoles(Collections.singleton(Role.ADMIN));
        }
        else {
            u.setRoles(Collections.singleton(Role.USER));
        }
        userRepository.save(u);

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin_users.html";
    }

    @PostMapping("/del_user")
    public String del_user( @RequestParam String del_name, Model model)
    {
        userRepository.deleteUserByUsername(del_name);
        //userRepository.deleteMyUsername(del_name);

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin_users.html";
    }

}
