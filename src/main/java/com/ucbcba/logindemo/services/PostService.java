package com.ucbcba.logindemo.services;

import com.ucbcba.logindemo.entities.Post;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface PostService {
    Iterable<Post> listAllPosts();

    void createPost(Post post);

    Post findPost(Integer id);

    void savePost(Post post);

    Post getPost(Integer id);

    void deletePost(Integer id);

    String getPostUserName(Integer id);

}
