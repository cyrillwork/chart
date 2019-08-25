package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.Role;
import com.cyrillwork.chart.User;
import com.cyrillwork.chart.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Controller
public class RegistrationController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public  String registration()
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model)
    {
        User user1 = userRepository.findUserByUsername(user.getUsername());
        if(user1 != null) {
            model.put("message", "User exist!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Role.USER);
        userRepository.save(user);

        return "redirect:/login";
    }
}
