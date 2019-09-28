package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.models.dto.ChartCaptchaResponse;
import com.cyrillwork.chart.properties.MainProperties;
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
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController
{
    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MainProperties mainProperties;

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
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult errors,
            Model model)
    {
        boolean errorCaptcha = false;
        boolean errorPassword = false;
        boolean errorUser = false;

        String url = String.format(mainProperties.getRecaptchaUrl(),
                mainProperties.getRecaptcha(), captchaResponse);

        ChartCaptchaResponse response = restTemplate.postForObject(url, Collections.emptyList(), ChartCaptchaResponse.class);

        if(errors.hasErrors())
        {
            return "registration";
        }

        if(!response.isSuccess())
        {
            model.addAttribute("captcha_error", true);
            errorCaptcha = true;
        }

        if( StringUtils.isEmpty(passwordConfirm) || (!passwordConfirm.equals(user.getPassword())) )
        {
            model.addAttribute("password_not_confirm", true);
            errorPassword = true;
        }

        if(userService.existOneMoreUser(user)) {
            model.addAttribute("user_exist", "Пользователь " + user.getUsername() + " уже существует");
            errorUser = true;
        }

        if(errorCaptcha || errorUser || errorPassword)
        {
            return "registration";
        }

        userService.saveUser(user, true);
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        User user = userService.activateUser(code);
        if(user != null){
            model.addAttribute("activate_mess", String.format("Пользовталь %s активирован",
                    user.getUsername()) );
        } else {
            model.addAttribute("activate_mess", "Пользователь не активирован");
        }
        return "login";
    }
}
