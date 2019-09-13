package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RegistrationController
{
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public  String registration(User user,
                                Model model)
    {
        model.addAttribute("passwordConfirm", "");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("passwordConfirm") String passwordConfirm,
            @Valid User user,
            BindingResult errors,
            Model model)
    {
        if(errors.hasErrors())
        {
            return "registration";
        }

        if( StringUtils.isEmpty(passwordConfirm) || (!passwordConfirm.equals(user.getPassword())) )
        {
            model.addAttribute("password_not_confirm", "Неправильно введен повторный пароль");
            return "registration";
        }

        if(!userService.saveUser(user, true)) {
            model.addAttribute("user_exist", "Пользователь " + user.getUsername() + " уже существует");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);
        if(isActivated){
            model.addAttribute("activate_mess", "Пользовталь активирован");
        } else {
            model.addAttribute("activate_mess", "Пользователь не активирован");
        }
        return "login";
    }
}
