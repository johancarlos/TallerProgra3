package com.ucbcba.logindemo.services;

import com.ucbcba.logindemo.entities.User;
import com.ucbcba.logindemo.repositories.RoleRepository;
import com.ucbcba.logindemo.repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static Log log = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRol(roleRepository.findById(1).get());
        userRepository.save(user);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Iterable<User> listAllUsers(){
        Iterable<User> users = userRepository.findAll();
        return  users;
    }
    @Override
    public String getLoggedInUserName() {
        try {
            return getLoggedInUser().getUsername();
        } catch (Exception ex) {
            throw new UsernameNotFoundException("Please Log in", ex);
        }
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        } else {
            throw new UsernameNotFoundException("User is not authenticated; Found " + authentication.getPrincipal() + " of type " + authentication.getPrincipal().getClass() + "; Expected type User");
        }
    }

    @Override
    public User findUserById(Integer id) {
        Optional<User> op;
        op = userRepository.findById(id);
        return op.get();
    }


}