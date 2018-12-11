package com.ucbcba.logindemo.controllers;


import com.ucbcba.logindemo.entities.Post;
import com.ucbcba.logindemo.entities.User;
import com.ucbcba.logindemo.services.PostService;
import com.ucbcba.logindemo.services.UserService;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.sql.SQLDataException;

@Controller
public class UserController {
    @Autowired
    UserService userService;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        User user = new User();
        user.setUsername("");
        user.setPassword("");

        model.addAttribute("user", user);
        return "user/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registration(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/register";
        }
        try {
            if (userService.findByUsername(user.getUsername()).getUsername().equals(user.getUsername()))
            {
                return "user/register";
            }
        }catch (NullPointerException ex)
        {
            user.setKarma(0);
            userService.save(user);
        }
        return "redirect:/";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
