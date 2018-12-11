package com.ucbcba.logindemo.controllers;

import com.ucbcba.logindemo.entities.*;
import com.ucbcba.logindemo.repositories.FavoritePostRepository;
import com.ucbcba.logindemo.repositories.HidedPostRepository;
import com.ucbcba.logindemo.repositories.ReportedPostRepository;
import com.ucbcba.logindemo.services.CategoryService;
import com.ucbcba.logindemo.services.PostService;
import com.ucbcba.logindemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


@Controller
public class PostController {
    private PostService postService;
    CategoryService categoryService;
    UserService userService;

    @Autowired
    private ReportedPostRepository reportedPostRepository;

    @Autowired
    private FavoritePostRepository favoritePostRepository;

    @Autowired
    private HidedPostRepository hidedPostRepository;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }
    @Autowired
    public void setCategoryService(CategoryService categoryService) { // si es que no existe un objeto categoryService te lo creo uno
        this.categoryService = categoryService;
    }

    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}

    public UserService getUserService(){return userService;}


    @RequestMapping(value = "/user/posts", method = RequestMethod.GET)
    public String list(Model model) {
        Iterable<Category> category = categoryService.listAllCategories();
        Iterable<Post> postList = postService.listAllPosts();
        Iterable<User> user = userService.listAllUsers();
        model.addAttribute("variableTexto","Hello");
        model.addAttribute("Categories",category);
        model.addAttribute("postList",postList);
        model.addAttribute("users",user);
        return "/user/posts";
    }

    @RequestMapping(value = "/post/upload-image", method = RequestMethod.POST)
    public ResponseEntity uploadImage(Model model, @RequestParam("file")MultipartFile file) throws Exception{
        File convFile = new File("src/main/resources/static/img/"+file.getOriginalFilename());

        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        Blob blob = new SerialBlob(file.getBytes());

        fos.write(file.getBytes());
        fos.close();
        System.out.println(convFile.getName());
        return ResponseEntity.ok("localhost:8090/img/" + file.getOriginalFilename());
    }

    @RequestMapping(value = "/newPost/{id}",method = RequestMethod.GET)
    public String newPost(@PathVariable Integer id, Model model ) {
        User user = userService.findUserById(id);
        Iterable<Category> category = categoryService.listAllCategories();
        Iterable<Post> post = postService.listAllPosts();
        model.addAttribute("categories",category);
        model.addAttribute("posts",post);
        model.addAttribute("user",user);
        return "newPost";
    }

    @RequestMapping(value = "/post/authors/{id}",method = RequestMethod.GET)
    public String authors(@PathVariable Integer id, Model model ) {
        List<User> items = new ArrayList<>();
        reportedPostRepository.findAll().forEach((r)-> {
            if (r.getPost() == id){
                items.add(userService.findUserById(r.getUser()));
            }
        });
        model.addAttribute("authors", items);
        return "authors";
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String save(@ModelAttribute("post")Post post)
    {
        postService.savePost(post);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/post/reportpost", method = RequestMethod.POST)
    public ResponseEntity reportPost(@ModelAttribute("post")ReportedPost pst)
    {
        reportedPostRepository.save(pst);
        return ResponseEntity.ok("your post has been reported");
    }


    @RequestMapping(value = "/post/favoritpost/{id}/{user}", method = RequestMethod.GET)
    public String favoritPost(@PathVariable Integer id, @PathVariable Integer user, Model model)
    {
        FavoritePost favorite = new FavoritePost();
        favorite.setPost(id);
        favorite.setUser(user);
        favoritePostRepository.save(favorite);
        return "redirect:/welcome";
    }


    @RequestMapping(value = "/post/hidepost", method = RequestMethod.POST)
    public ResponseEntity hidepost(@ModelAttribute("post") HidedPost pst)
    {
        hidedPostRepository.save(pst);
        return ResponseEntity.ok("your post has been hided");
    }


    @RequestMapping(value = "/user/profile/{id}")
    public String profile(@PathVariable Integer id, Model model)
    {
        User user = userService.findUserById(id);
        User userLogged = userService.findUserById(id);
        Iterable<Post> postList = postService.listAllPosts();
        model.addAttribute("karma", user.getKarma());
        model.addAttribute("postList", postList);
        model.addAttribute("user",user.getUsername());
        model.addAttribute("userLogged", userLogged.getId());
        return "user/profile";
    }

    @RequestMapping(value = "/user/profile/favoritePost/{id}")
    public String favorites(@PathVariable Integer id, Model model)
    {
        User user = userService.findUserById(id);
        User userLogged = userService.findUserById(id);
        model.addAttribute("user",user.getUsername());
        model.addAttribute("userLogged", userLogged.getId());
        return "favorites";
    }



    @RequestMapping("/post/{id}/{uid}")
    public String show(@PathVariable Integer id, @PathVariable Integer uid, Model model) {
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        model.addAttribute("uid", uid);
        return "showPost";
    }

    @RequestMapping(value = "/editPost/{id}",method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {
        Iterable<Category> iteCat = categoryService.listAllCategories();
        Post post = postService.findPost(id);
        model.addAttribute("categories", categoryService.listAllCategories());
        model.addAttribute("post", post);
        return "editPost";
    }


    @RequestMapping("/deletePost/{id}")
    public String delete(@PathVariable Integer id) {
        postService.deletePost(id);
        return "redirect:/user/posts";
    }

    @RequestMapping(value = "/postlike/{id}", method = RequestMethod.GET)
    public String like(@PathVariable Integer id, Model model) {
        Post post = postService.getPost(id);
        userService.findUserById(post.getUser().getId()).setKarma(userService.findUserById(post.getUser().getId()).getKarma() + 1);
        post.setLikes(post.getLikes()+1);
        postService.savePost(post);
        return "redirect:/welcome";
    }


    @RequestMapping(value ="/postunlike/{id}",method = RequestMethod.GET)
    public String dislike(@PathVariable Integer id, Model model)
    {
        Post post = postService.getPost(id);
        if (0<post.getLikes())
        {
            userService.findUserById(post.getUser().getId()).setKarma(userService.findUserById(post.getUser().getId()).getKarma() - 1);
            post.setLikes(post.getLikes()-1);
            postService.savePost(post);
        }
        return "redirect:/welcome";
    }



    @RequestMapping("/")
    public String main(Model model) {
        Iterable<Post> postList = postService.listAllPosts();
        model.addAttribute("postList",postList);
        return "root";
    }

    @RequestMapping(value = "/postlike-rest/{id}", method = RequestMethod.GET)
    public ResponseEntity likeRest(@PathVariable Integer id, Model model) {
        Post post = postService.getPost(id);
        userService.findUserById(post.getUser().getId()).setKarma(userService.findUserById(post.getUser().getId()).getKarma() + 1);
        post.setLikes(post.getLikes()+1);
        postService.savePost(post);
        return ResponseEntity.ok("yess");
    }

    @RequestMapping(value = "/postunlike-rest/{id}", method = RequestMethod.GET)
    public ResponseEntity unlikeRest(@PathVariable Integer id, Model model) {
        Post post = postService.getPost(id);
        userService.findUserById(post.getUser().getId()).setKarma(userService.findUserById(post.getUser().getId()).getKarma() + 1);
        post.setLikes(post.getLikes()-1);
        postService.savePost(post);
        return ResponseEntity.ok("yess");
    }
}