package com.cyrillwork.chart.service;

import com.cyrillwork.chart.User;
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

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    public void deleteUserByUsername(String del_name){
        userRepository.deleteUserByUsername(del_name);
    }

    public Iterable<User> findAllUsers()
    {
        return userRepository.findAll();
    }
}
