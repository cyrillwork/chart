package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.Role;
import com.cyrillwork.chart.User;
import com.cyrillwork.chart.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public  String admin_users(Model model)
    {
        updateAdminUsers(model);
        return "admin_users.html";
    }

    @PostMapping("/add_user")
    public String add_user(  @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam Map<String, String> form,
                             Model model)
    {
        if(userRepository.findUserByUsername(username) != null) {
            model.addAttribute("mess_error", username + " уже есть");
            updateAdminUsers(model);
            return "admin_users.html";
        }

        User u = new User();
        u.setUsername(username);
        u.setPassword(password);

        // Set<String> setRoles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        String r = form.get("user_role");

        if(r != null) {
            u.setRoles(Role.valueOf(r));
        }
        else {
            u.setRoles(Role.USER);
        }
        userRepository.save(u);

        updateAdminUsers(model);

        return "admin_users.html";
    }


    @PostMapping("/del_user")
    public String del_user( @RequestParam String del_name, Model model)
    {
        userRepository.deleteUserByUsername(del_name);
        updateAdminUsers(model);
        return "admin_users.html";
    }

    public void updateAdminUsers(Model model)
    {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", Role.values());
    }

}
