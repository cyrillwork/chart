package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.Role;
import com.cyrillwork.chart.User;
import com.cyrillwork.chart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController
{
    @Autowired
    private UserService userService;

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

        User getUser = userService.findUserByUsername(user.getUsername());

        if(getUser != null) {
            model.put("user_exist", "Пользователь " + getUser.getUsername() +   " уже существует");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Role.USER);
        userService.saveUser(user);

        return "redirect:/login";
    }
}
