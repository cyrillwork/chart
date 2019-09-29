package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.models.dto.ChartCaptchaResponse;
import com.cyrillwork.chart.properties.MainProperties;
import com.cyrillwork.chart.service.UserService;
import org.junit.Assert;
import org.junit.Before;
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
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationControllerTest {
    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private MainProperties mainProperties;

    @MockBean
    private UserService userService;

    @MockBean
    private RestTemplate restTemplate;

    private User user;
    private String captchaResponse;
    private BindingResult errors;
    private Model model;

    @Before
    public void setup(){
        user = new User();
        captchaResponse = "12345";
        errors = mock(BindingResult.class);
        model = mock(Model.class);

        ChartCaptchaResponse response = new ChartCaptchaResponse();

        String url = String.format(mainProperties.getRecaptchaUrl(),
                mainProperties.getRecaptcha(), captchaResponse);
        response.setSuccess(true);

        Mockito.doReturn(response).
                when(restTemplate).
                postForObject(url, Collections.emptyList(), ChartCaptchaResponse.class);
    }

    @Test
    public void addUserConfirmPassword() {

        user.setPassword("123456");

        Assert.assertEquals("registration", registrationController.addUser("12345",
                captchaResponse,
                user,
                errors,
                model));

        Mockito.verify(model, Mockito.times(1)).addAttribute (
                    ArgumentMatchers.eq("password_not_confirm")
                , ArgumentMatchers.eq(true)
        );
    }

    @Test
    public void addUserExistUser()
    {
        user.setPassword("12345");

        Mockito.doReturn(true).
                when(userService).
                existOneMoreUser(user);

        Assert.assertEquals("registration", registrationController.addUser("12345",
                captchaResponse,
                user,
                errors,
                model));

        Mockito.verify(model, Mockito.times(1)).addAttribute (
                ArgumentMatchers.eq("user_exist")
                , ArgumentMatchers.anyString()
        );

    }


}
