package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.Message;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserMessagesController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/user_messages/{user}")
    public String showUserMessages(@PathVariable User user,
                                   Model model) {
        model.addAttribute("messages", messageService.findAllByUser(user));
        return "/user_messages";
    }

    @GetMapping("/del_message/{message}")
    public String deleteMessage(@PathVariable Message message,
                                   Model model) {
        User user = message.getUser();

        messageService.deleteMessage(message);
        model.addAttribute("messages", messageService.findAllByUser(user));

        return "/user_messages";
    }

}
