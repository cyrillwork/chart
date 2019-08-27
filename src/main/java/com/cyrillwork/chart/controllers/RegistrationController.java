package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.Role;
import com.cyrillwork.chart.User;
import com.cyrillwork.chart.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Controller
public class RegistrationController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public  String registration(User user)
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult errors, Map<String, Object> model)
    {
        if(errors.hasErrors())
        {
            return "registration";
        }

        User user1 = userRepository.findUserByUsername(user.getUsername());

        if(user1 != null) {
            model.put("user_exist", "Пользователь " + user.getUsername() +   " уже существует");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Role.USER);
        userRepository.save(user);

        return "redirect:/login";
    }
}
