package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.Role;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/user_edit")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserEdit {
    @Autowired
    UserService userService;

    @GetMapping("{id}")
    public String userEditById(@PathVariable Long id, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", userService.findUserById(id));
        return "user_edit";
    }

    @PostMapping
    public String userSave(
            @Valid User user,
            BindingResult errors,
            @RequestParam Map<String, String> form,
            Model model)
    {
        boolean isExistUser = false;

        if( errors.hasErrors() || (isExistUser = userService.existOneMoreUser(user)) )
        {
            String str_error = new String("");

            if(isExistUser)
            {
                str_error += "Пользователь " + user.getUsername() + " уже есть !!!";
            }

//            String str_error = new String("Ошибки ввода:");
//            for(FieldError iii: errors.getFieldErrors())
//            {
//                str_error += " " + iii.getDefaultMessage();
//            }

            model.addAttribute("error_exist", str_error );
            model.addAttribute("roles", Role.values());
            model.addAttribute("user", user);
            //return "redirect:/user_edit/" + user.getId();
            return "user_edit";
        }

        String r = form.get("user_role");
        if(r != null) {
            user.setRole(Role.valueOf(r));
        }

        userService.updateUser(user);
        return "redirect:/admin_users";
    }

}
