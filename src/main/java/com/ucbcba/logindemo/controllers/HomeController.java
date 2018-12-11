package com.ucbcba.logindemo.controllers;


import com.ucbcba.logindemo.entities.FavoritePost;
import com.ucbcba.logindemo.entities.Post;
import com.ucbcba.logindemo.repositories.FavoritePostRepository;
import com.ucbcba.logindemo.repositories.HidedPostRepository;
import com.ucbcba.logindemo.repositories.ReportedPostRepository;
import com.ucbcba.logindemo.services.PostService;
import com.ucbcba.logindemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {


    @Autowired
    PostService postService;


    @Autowired
    UserService userService;

    @Autowired
    ReportedPostRepository reportedPostRepository;

    @Autowired
    FavoritePostRepository favoritePostRepository;

    @Autowired
    HidedPostRepository hiddenPostRepository;



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String registrationInit(Model model) {
        model.addAttribute("postList", postService.listAllPosts());
        //model.addAttribute("reportedpostlist", reportedPostWithPost.getReportedPost());
        return "home";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model)
    {
        model.addAttribute("postList", postService.listAllPosts());
        model.addAttribute("postService", postService);
        model.addAttribute("reportedPosts", reportedPostRepository.findAll());
        return "admin";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(Model model) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        com.ucbcba.logindemo.entities.User userLooged=userService.findByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("userLooged", userLooged.getId());
        System.out.println(userLooged.getId());

        if (user.getAuthorities().toArray()[0].toString().equals("ADMIN")){
            return "redirect:/admin";
        }
        Iterable<Post> postList = postService.listAllPosts();
        model.addAttribute("postList",postList);
        model.addAttribute("postService", postService);
        model.addAttribute("reportedPost", reportedPostRepository);
        model.addAttribute("favoritePost",favoritePostRepository);
        model.addAttribute("hidenPost", hiddenPostRepository);
        return "welcome";
    }
}


