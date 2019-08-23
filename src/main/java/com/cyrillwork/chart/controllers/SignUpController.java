package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.SignUpUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/sign_up")
public class SignUpController {

    @GetMapping
    public String showSignUpForm(Model model)
    {
        model.addAttribute("sign_up", new SignUpUser());
        return "registration";
    }

    @PostMapping
    public String processSignUp(SignUpUser signUpUser)
    {
        log.info("Sign up user" + signUpUser);
        return "index";
    }

}
