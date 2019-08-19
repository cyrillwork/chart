package com.cyrillwork.chart;

import com.cyrillwork.chart.repos.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Controller
//@RequestMapping(path = "/mysql")
public class UsersController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public  String main(Map<String, Object> model)
    {
        Iterable<Message> users = userRepository.findAll();
        model.put("users", users);
        return "main";
    }

    @PostMapping("add")
    public String add(  @RequestParam String name,
                        @RequestParam String email,
                        @RequestParam Integer age,
                        Map<String, Object> model)
    {
        userRepository.save(new Message(name, email, age));

        Iterable<Message> users = userRepository.findAll();

        model.put("users", users);

        return "main";
    }

    @PostMapping("delete")
    public String delete(  @RequestParam String del_name,
                            Map<String, Object> model)
    {
        userRepository.deleteMessagesByName(del_name);
        Iterable<Message> all = userRepository.findAll();
        model.put("users", all);

        return "main";
    }

}
