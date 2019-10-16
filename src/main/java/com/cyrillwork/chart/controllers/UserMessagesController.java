package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.Message;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@Slf4j
public class UserMessagesController {
    @Autowired
    private MessageService messageService;

    private final String url = "user_messages";
    @GetMapping("/user_messages/{user}")
    public String showUserMessages(@PathVariable User user,
                                   Model model,
                                   @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
                                   ) {
        log.info("!!!!!!!!!1 showUserMessages");
        model.addAttribute("messages", messageService.findAllByUser(user, pageable));
        model.addAttribute("url", url);
        model.addAttribute("user_id", user.getId());
        return url;
    }

    @GetMapping("/del_message/{message}")
    public String deleteMessage(    @PathVariable Message message,
                                    Model model,
                                    @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
                                ) {
        log.info("!!!!!!!!!1 deleteMessage");
        User user = message.getUser();
        messageService.deleteMessage(message);
        model.addAttribute("messages", messageService.findAllByUser(user, pageable));
        model.addAttribute("url", url);
        model.addAttribute("user_id", user.getId());
        return url;
    }

}
