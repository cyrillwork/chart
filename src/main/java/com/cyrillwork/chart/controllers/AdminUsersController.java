package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.Message;
import com.cyrillwork.chart.repos.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AdminUsersController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin_users")
    public  String main(Map<String, Object> model)
    {
        Iterable<Message> users = userRepository.findAll();
        model.put("users", users);
        return "admin_users";
    }

    @PostMapping("/add")
    public String add(  @RequestParam String name,
                        @RequestParam String email,
                        @RequestParam Integer age,
                        Map<String, Object> model)
    {
        userRepository.save(new Message(name, email, age));

        Iterable<Message> users = userRepository.findAll();

        model.put("users", users);

        return "admin_users";
    }

    @PostMapping("/delete")
    public String delete(  @RequestParam String del_name,
                            Map<String, Object> model)
    {
        userRepository.deleteMessagesByName(del_name);
        Iterable<Message> all = userRepository.findAll();
        model.put("users", all);

        return "admin_users";
    }

}
