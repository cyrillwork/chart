package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.properties.MainProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class MainController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MainProperties mainProperties;

//    @Autowired
//    private ServletContext servletContext;

    @GetMapping("/login")
    public String showLogin(Model model)
    {
        log.info(mainProperties.getGreeting());
        model.addAttribute("greeting", mainProperties.getGreeting());
        return "login";
    }

    @GetMapping("/")
    public String showMain(
            @AuthenticationPrincipal User user,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response)
    {
//        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//        SessionRegistry sReg = (SessionRegistry) ac.getBean("sessionRegistry");
//
//        if(sReg != null) {
//            for (Object principal : sReg.getAllPrincipals())
//                System.out.println("Principal: " + principal.toString());
//        }

        HttpSession session = request.getSession(false);

        //System.out.println("session=" + session);
        log.info("session=" + session);

        if(session == null)
        {
            log.info("Create new session");
            session = request.getSession(true);
        }

        model.addAttribute("login_user", user);
        model.addAttribute("greeting", mainProperties.getGreeting());

        return "index";
    }

}
