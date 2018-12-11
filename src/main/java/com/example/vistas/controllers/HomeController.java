package com.example.vistas.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebParam;

@Controller
public class HomeController
{
    @Value("${spring.application.name}")
    String appName;


    @RequestMapping(value = "/hm", method = RequestMethod.GET)
    public String homePage(Model model) {

        return "home";
    }

    @RequestMapping(value = "/reg",method = RequestMethod.GET)
    public String newRegistro(Model model) {

        return "registro";
    }

    @RequestMapping (value = "/hmlog", method = RequestMethod.GET)
    public String homeLogin(Model model)
    {
        return "homelogin";
    }

    @RequestMapping(value = "/newpost",method = RequestMethod.GET)
    public String newPost(Model model) {

        return "newpost";
    }
}
