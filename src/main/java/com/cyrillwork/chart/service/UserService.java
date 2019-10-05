package com.cyrillwork.chart.service;

import com.cyrillwork.chart.models.Role;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.properties.MainProperties;
import com.cyrillwork.chart.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private MainProperties mainProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public User findUserById(Long id) { return userRepository.findUserById(id); }

    public boolean saveUser(User user, boolean hasMailCheck) {
        if( userRepository.findUserById(user.getId()) != null)
        {
            return false;
        }
        if(!user.hasRole())
        {
            user.setRole(Role.USER);
        }
        user.setActive(true);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        if(hasMailCheck) {
            mailService.send(user);
        }

        return true;
    }

    public boolean updateUser(User user){
        return updateUser(user, false);
    }

    public boolean updateUser(User user, boolean isProfile){
        User db_user = userRepository.findUserById(user.getId());
        if( db_user ==  null)
        {
            return false;
        }

        db_user.setUsername(user.getUsername());
        db_user.setEmail(user.getEmail());
        db_user.setActive(user.isActive());
        if(!isProfile) {
            db_user.setRoles(user.getRoles());
            db_user.setPassword(user.getPassword());
            db_user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(db_user);
        return true;
    }

    public boolean deleteUserById(Long id){
        boolean result = false;
        if(userRepository.findUserById(id) != null)
        {
            result = true;
            userRepository.deleteUserById(id);
        }
        return result;
    }

    public User activateUser(String code) {
        User user = userRepository.findUserByActivationCode(code);
        if(user != null)
        {
            user.setActivationCode("");
            userRepository.save(user);
        }
        return user;
    }

    public boolean existOneMoreUser(User user) {
        boolean result = false;
        User user_bd = userRepository.findUserByUsername(user.getUsername());
        if(user_bd != null)
        {
            if(user_bd.getId() != user.getId())
            {
                result = true;
            }
        }
        return result;
    }
}
