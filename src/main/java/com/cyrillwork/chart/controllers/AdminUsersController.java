package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.Role;
import com.cyrillwork.chart.User;
import com.cyrillwork.chart.repos.UserRepository;
import com.sun.tracing.dtrace.ModuleAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
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
    public String add_user(  @Valid User user,
                             BindingResult errors,
                             @RequestParam Map<String, String> form,
                             Model model)
    {
        if(errors.hasErrors())
        {
            updateAdminUsers(model);
            String str_error = new String("Ошибки ввода:");

            for(FieldError iii: errors.getFieldErrors())
            {
                str_error += " " + iii.getDefaultMessage();
            }
            model.addAttribute("error_exit", str_error );
            return "admin_users";
        }

        if(userRepository.findUserByUsername(user.getUsername()) != null)
        {
            model.addAttribute("user_exist",  "Пользователь " + user.getUsername() + " уже есть");
            updateAdminUsers(model);
            return "admin_users";
        }

        // Set<String> setRoles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        String r = form.get("user_role");

        if(r != null) {
            user.setRoles(Role.valueOf(r));
        }
        else {
            user.setRoles(Role.USER);
        }
        userRepository.save(user);

        updateAdminUsers(model);
        return "admin_users";
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
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
    }

}
