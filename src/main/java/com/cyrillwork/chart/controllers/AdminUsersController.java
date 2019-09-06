package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.Role;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminUsersController
{
    @Autowired
    private UserService userService;

    @GetMapping("/admin_users")
    public  String admin_users(@AuthenticationPrincipal User login_user, Model model)
    {
        model.addAttribute("login_user", login_user.getUsername());
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
            model.addAttribute("error_exist", str_error );
            return "admin_users";
        }

        if(userService.findUserByUsername(user.getUsername()) != null)
        {
            model.addAttribute("user_exist",  "Пользователь " + user.getUsername() + " уже есть");
            updateAdminUsers(model);
            return "admin_users";
        }

        String r = form.get("user_role");
        if(r != null) {
            user.setRole(Role.valueOf(r));
        }
        else {
            user.setRole(Role.USER);
        }
        user.setActive(true);
        userService.saveUser(user, false);

        updateAdminUsers(model);
        return "admin_users";
    }

    @GetMapping("/del_user/{user}")
    public String del_user(@PathVariable User user, Model model)
    {
        if(!userService.deleteUserById(user.getId()))
        {
            model.addAttribute("user_no_exist",  "Пользователь " + user.getUsername() + " не найден");
        }
        updateAdminUsers(model);
        return "admin_users.html";
    }

    public void updateAdminUsers(Model model)
    {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
    }

}
