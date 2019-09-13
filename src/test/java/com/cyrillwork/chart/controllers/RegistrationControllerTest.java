package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationControllerTest {
    @Autowired
    private RegistrationController registrationController;

    @MockBean
    private UserService userService;

    @Test
    public void addUserConfirmPassword() {
        User user = new User();
        BindingResult errors = mock(BindingResult.class);
        Model model = mock(Model.class);

        user.setPassword("123456");

        Assert.assertEquals("registration", registrationController.addUser("12345",
                user,
                errors,
                model));

        Mockito.verify(model, Mockito.times(1)).addAttribute (
                    ArgumentMatchers.eq("password_not_confirm")
                , ArgumentMatchers.anyString()
        );
    }

    @Test
    public void addUserExistUser()
    {
        User user = new User();
        BindingResult errors = mock(BindingResult.class);
        Model model = mock(Model.class);

        user.setPassword("12345");

        Mockito.doReturn(false).
                when(userService).
                saveUser(user, true);

        Assert.assertEquals("registration", registrationController.addUser("12345",
                user,
                errors,
                model));

        Mockito.verify(model, Mockito.times(1)).addAttribute (
                ArgumentMatchers.eq("user_exist")
                , ArgumentMatchers.anyString()
        );

    }
}
