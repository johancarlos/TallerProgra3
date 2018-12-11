package com.ucbcba.logindemo.controllers;


import com.ucbcba.logindemo.entities.Comment;
import com.ucbcba.logindemo.entities.Post;
import com.ucbcba.logindemo.services.CommentService;
import com.ucbcba.logindemo.services.PostService;
import com.ucbcba.logindemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller
public class CommentController
{
    private CommentService commentService;

    @Autowired
    private UserService userService;


    @Autowired
    public void setCommentService(CommentService commentService)
    {
        this.commentService = commentService;
    }

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService)
    {
        this.postService = postService;
    }

    @RequestMapping (value = "/comment", method = RequestMethod.POST)
    public String save (Comment comment, @RequestParam("value1") String valueOne)
    {
        Integer v = Integer.parseInt(valueOne);
        comment.setUser(userService.findUserById(v));
        commentService.saveComment(comment);
        return "redirect:/post/" + comment.getPost().getId() + "/" + v;
    }

    public static <T> T getLastElement(final Iterable<T> elements) {
        final Iterator<T> itr = elements.iterator();
        T lastElement = itr.next();

        while(itr.hasNext()) {
            lastElement=itr.next();
        }

        return lastElement;
    }

    @RequestMapping(value = "/comment-rest", method = RequestMethod.POST)
    public ResponseEntity hidepost(Comment comment, @RequestParam("value1") String valueOne)
    {
        Integer v = Integer.parseInt(valueOne);
        comment.setUser(userService.findUserById(v));
        commentService.saveComment(comment);
        HashMap<String, String> map = new HashMap<>();
        map.put("text", comment.getText());
        map.put("likes", comment.getLikes().toString());
        map.put("id", getLastElement(commentService.listAllComments()).getId().toString());
        map.put("post", getLastElement(commentService.listAllComments()).getPost().getId().toString());
        map.put("user", getLastElement(commentService.listAllComments()).getUser().getId().toString());
        return ResponseEntity.ok(map);
    }

    @RequestMapping(value="/commentlike/{id}/{idpost}/{uid}",method = RequestMethod.GET)
    public String like(@PathVariable Integer id, @PathVariable Integer idpost,@PathVariable Integer uid, Model model)
    {
        Post post = postService.getPost(idpost);
        Comment comment = commentService.getComment(id);
        userService.findUserById(comment.getUser().getId()).setKarma(userService.findUserById(comment.getUser().getId()).getKarma() + 1);
        comment.setLikes(comment.getLikes()+1);
        commentService.saveComment(comment);
        return "redirect:/post/"+ post.getId() + "/" + uid;
    }



    @RequestMapping(value ="/commentdislike/{id}/{idpost}/{uid}",method = RequestMethod.GET)
    public String dislike(@PathVariable Integer id, @PathVariable Integer idpost,@PathVariable Integer uid, Model model)
    {
        Post post = postService.getPost(idpost);
        Comment comment = commentService.getComment(id);
        userService.findUserById(comment.getUser().getId()).setKarma(userService.findUserById(comment.getUser().getId()).getKarma() - 1);
        if (0<comment.getLikes())
        {
            comment.setLikes(comment.getLikes()-1);
            commentService.saveComment(comment);
        }
        return "redirect:/post/"+ post.getId() + "/" + uid;
    }

    public static <E> Collection<E> makeCollection(Iterable<E> iter) {
        Collection<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }
    @RequestMapping(value ="/lastcomment",method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity lastcomment()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("text", getLastElement(makeCollection(commentService.listAllComments())).getText());
        map.put("foo", "bar");
        map.put("aa", "bb");
        return ResponseEntity.ok(map);
    }
}
