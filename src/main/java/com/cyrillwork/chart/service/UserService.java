package com.cyrillwork.chart.service;

import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

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

    public void saveUser(User user)
    {
        userRepository.save(user);
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


}
