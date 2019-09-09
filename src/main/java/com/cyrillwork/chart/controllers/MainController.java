package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showMain(@AuthenticationPrincipal User user,
                         Model model){

        model.addAttribute("login_user", user);

        return "index.html";
    }

}
