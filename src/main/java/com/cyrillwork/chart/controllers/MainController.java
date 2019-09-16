package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
//    @Autowired
//    private ServletContext servletContext;
    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

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
            LOG.info("Create new session");
            session = request.getSession(true);
        }

        model.addAttribute("login_user", user);
        return "index.html";
    }

}
