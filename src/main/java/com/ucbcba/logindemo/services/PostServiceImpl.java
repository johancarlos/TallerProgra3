package com.ucbcba.logindemo.services;

import com.ucbcba.logindemo.entities.Post;
import com.ucbcba.logindemo.entities.User;
import com.ucbcba.logindemo.repositories.PostRepository;
import com.ucbcba.logindemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Qualifier(value = "postRepository")
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository =  postRepository;
    }


    @Override
    public Iterable<Post> listAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }


    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Post getPost(Integer id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    public String getPostUserName(Integer id) {
        List<User> list = userRepository.findAll();
        for (User e:
             list) {
           if(e.getId() == id){
               return e.getUsername();
           }
        }

        return "ANONYMUS";
    }

    @Override
    public Post findPost(Integer id){
        Optional<Post> opt;
        opt = postRepository.findById(id);
        return opt.get();
    }

}
