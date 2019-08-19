package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public  String getLoginForm(){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute(name = "loginForm") LoginForm loginForm, Model model)
    {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        if("admin".equals(username) && "admin".equals(password))
        {
            return "index";
        }

        //model.addAllAttributes();
        //"invalidCredentials", true);

        return "login";
    }
}
