package com.cyrillwork.chart.service;

import com.cyrillwork.chart.models.Role;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private String port;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    public Iterable<User> findAllUsers()
    {
        return userRepository.findAll();
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public boolean saveUser(User user, boolean hasMailCheck)
    {
        if( userRepository.findUserById(user.getId()) != null)
        {
            return false;
        }
        user.setActive(true);
        user.setRole(Role.USER);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if(hasMailCheck) {
            String message = String.format("Привет %s!\n" +
                            "Добро пожаловать в простой чат. Для активации пользователя перейдите по ссылке http://%s:%s/activate/%s",
                    host, port,
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailService.send(user.getEmail(), "Activation code", message);
        }

        return true;
    }

    public boolean updateUser(User user){
        User db_user = userRepository.findUserById(user.getId());
        if( db_user ==  null)
        {
            return false;
        }
        db_user.setUsername(user.getUsername());
        db_user.setRoles(user.getRoles());
        db_user.setEmail(user.getEmail());
        db_user.setActive(user.isActive());

        userRepository.save(db_user);
        return true;
    }

    public boolean deleteUserByUsername(String del_name){
        boolean result = false;
        if(userRepository.findUserByUsername(del_name) != null)
        {
            result = true;
            userRepository.deleteUserByUsername(del_name);
        }
        return result;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findUserByActivationCode(code);
        if(user == null)
        {
            return false;
        }
        return true;
    }
}
