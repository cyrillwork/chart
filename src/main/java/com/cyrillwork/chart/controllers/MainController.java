package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.properties.MainProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class MainController {

    @Autowired
    private MainProperties mainProperties;

    @GetMapping("/login{lang}")
    public String showLogin(@PathVariable String lang, Model model)
    {
        //log.info(mainProperties.getGreeting());
        //model.addAttribute("greeting", mainProperties.getGreeting());
        return "login";
    }

    @GetMapping("/")
    public String showMain(
            @AuthenticationPrincipal User user,
            Model model)
    {
        model.addAttribute("login_user", user);
        //model.addAttribute("greeting", mainProperties.getGreeting());

        return "index";
    }

}
