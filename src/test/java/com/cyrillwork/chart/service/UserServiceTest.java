package com.cyrillwork.chart.service;

import com.cyrillwork.chart.models.Role;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.repos.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MailService mailService;

    @MockBean
    private PasswordEncoder passwordEncoder;


    @Test
    public void saveUserWithMailCheck() {
        boolean mailCheck = true;
        User user = new User();

        user.setEmail("user@mail.com");

        Assert.assertTrue(userService.saveUser(user, mailCheck));
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);

        Mockito.verify(mailService, Mockito.times(1)).send
                (
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.contains("Добро пожаловать")
                );
    }

    @Test
    public void saveUserNotMailCheck() {
        User user = new User();

        Assert.assertTrue(userService.saveUser(user, false));
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);

        Mockito.verify(mailService, Mockito.times(0)).send
                (
                        ArgumentMatchers.any(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    public void saveUserFail() {
        final Long id = 42l;
        User user = new User();

        user.setId(id);

        Mockito.doReturn(new User()).
                when(userRepository).
                findUserById(id);

        Assert.assertFalse(userService.saveUser(user, false));
    }

}
