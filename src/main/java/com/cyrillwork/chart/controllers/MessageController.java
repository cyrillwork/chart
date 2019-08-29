package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.Message;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public String show_messages(Model model){
        prepareModel(model);
        return "messages.html";
    }

    @PostMapping("/messages")
    public  String input_message(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult result, Model model){

        message.setUser(user);

        messageService.saveMessage(message);

        prepareModel(model);
        return "messages.html";
    }


    public void prepareModel(Model model){
        model.addAttribute("all_messages", messageService.findAllMessages());
        model.addAttribute("message", new Message());
    }


}
