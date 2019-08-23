package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.LoginForm;
import com.cyrillwork.chart.SignUpUser;
import com.cyrillwork.chart.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public  String registration()
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(SignUpUser user, Map<String, Object> model)
    {
        SignUpUser user1 = userRepository.findSignUpUserByUsername(user.getUsername());
        if(user1 != null)
        {
            model.put("message", "User exist!");
            return "registration";
        }

        return "redirect:/login";
    }
}
